import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class RemoteHost {
    public static void main(String[] args) throws Exception {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Remote Host running on port " + port);

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        Robot robot = new Robot();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        while (true) {
            // Capture the screen
            BufferedImage screenCapture = robot.createScreenCapture(
                    new Rectangle(screenSize));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenCapture, "jpg", baos);

            // Send the screen image
            out.writeObject(baos.toByteArray());
            out.flush();

            // Receive and execute client commands
            try {
                Object input = in.readObject();
                if (input instanceof String) {
                    String command = (String) input;
                    executeCommand(command, robot);
                }
            } catch (Exception e) {
                System.out.println("Client disconnected.");
                break;
            }
        }

        clientSocket.close();
        serverSocket.close();
    }

    private static void executeCommand(String command, Robot robot) {
        try {
            if (command.startsWith("MOVE")) {
                String[] parts = command.split(" ");
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                robot.mouseMove(x, y);
            } else if (command.startsWith("CLICK")) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            } else if (command.startsWith("TYPE")) {
                String[] parts = command.split(" ", 2);
                robot.keyPress(parts[1].charAt(0));
                robot.keyRelease(parts[1].charAt(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
