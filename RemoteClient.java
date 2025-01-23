import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class RemoteClient {
    public static void main(String[] args) throws Exception {
        String hostIP = JOptionPane.showInputDialog("Enter Host IP Address:");
        int port = 12345;

        Socket socket = new Socket(hostIP, port);
        System.out.println("Connected to the host.");

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        JFrame frame = new JFrame("Remote Client");
        JLabel screenLabel = new JLabel();
        frame.add(screenLabel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    out.writeObject("CLICK");
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    out.writeObject("MOVE " + e.getX() + " " + e.getY());
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    out.writeObject("TYPE " + e.getKeyChar());
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        while (true) {
            try {
                byte[] imageBytes = (byte[]) in.readObject();
                ImageIcon screen = new ImageIcon(imageBytes);
                screenLabel.setIcon(screen);
                frame.repaint();
            } catch (Exception e) {
                System.out.println("Host disconnected.");
                break;
            }
        }

        socket.close();
    }
}
