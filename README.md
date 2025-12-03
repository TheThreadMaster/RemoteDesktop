# RemoteDesktop

RemoteDesktop is a Java-based project for providing remote desktop functionality. This repository contains the source code (100% Java) and supporting files for building, running, and developing the RemoteDesktop application.

## Summary

RemoteDesktop enables remote screen viewing and basic interaction between machines, implemented in Java for portability.

## Implemented Features

This section lists the features that are confirmed implemented in the codebase:

- **Remote screen streaming**: The server captures the screen, encodes it as a JPEG image, and sends it to the client.
- **Basic remote control (mouse & keyboard)**:
  - The client can send mouse click events, mouse movements, and keyboard key presses to the host.
  - The host receives these commands and injects them via the Java Robot API.
- **Single connection (one client to one server at a time)**: The current implementation accepts a single client connection.

**Not implemented**
- No TLS or encrypted channel.
- No authentication (any client can connect).
- No file transfer.
- No clipboard synchronization.
- No session logging.
- No multi-session/connection management.
- No GUI for connection management.
- No cross-platform packaging/installer.
- No flexible configuration (port is hardcoded).
- No authorization or access control.

## Requirements

- Java Development Kit (JDK) 11 or newer.
- Manual compilation or using Java command-line tools.
- OS: Should work on Windows, macOS, and Linux (tested only where Java's AWT Robot and Swing APIs are available).

## Build

To compile both server and client components:

```bash
# Example for simple javac build (adjust if files are in packages):
javac RemoteHost.java
javac RemoteClient.java
```

## Run

Start the server (host) on the remote machine:
```bash
java RemoteHost
```

Start the client and connect to the host's IP address:
```bash
java RemoteClient
```
You will be prompted for the Host IP Address.

## Usage

1. Start the server on the remote machine (host).
2. Start the client, provide the server's IP, and connect.
3. The client window will display the remote screen, and you can control the host using your mouse and keyboard (basic input events are supported).

## Architecture (brief)

- **Server (RemoteHost):** Captures the full screen, streams it as images over TCP, and listens for input commands (mouse/keyboard) from the client to execute on the host.
- **Client (RemoteClient):** Connects to the server, receives and displays the remote screen, sends user mouse and keyboard events back to the host.
- **Network:** Custom protocol, plain TCP sockets using Java Object streams.
- **Security:** None (no encryption, no authentication).

## Contributing

See guidelines in the original README. Please discuss major changes via issues before pull requests.

## License

No LICENSE file provided. The code is not currently licensed for open-source use.

## Roadmap / TODO

- Add authentication and encrypted connection (TLS/SSL).
- Improve input fidelity (mouse buttons, key combinations, etc.).
- Add file transfer, clipboard sync, and multi-session support.
- Implement proper configuration for port and host settings.
- Enhance GUI and error handling.

## Contact

Repository: https://github.com/TheThreadMaster/RemoteDesktop  
Maintainer: TheThreadMaster

---
