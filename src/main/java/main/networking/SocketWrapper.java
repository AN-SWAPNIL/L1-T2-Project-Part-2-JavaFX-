package main.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public SocketWrapper(String s, int port) throws IOException { // used by the client
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public SocketWrapper(Socket s) throws IOException { // used by the server
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() {
        try {
            return ois.readUnshared();
        } catch (IOException e) {
            throw new RuntimeException("Problem in reading1");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Problem in reading2");
        }
    }

    public void write(Object o) {
        try {
            oos.writeUnshared(o);
        } catch (IOException e) {
            throw new RuntimeException("Problem in writing");
        }
    }

    public void closeConnection() {
        try {
            ois.close();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Problem in closing");
        }
    }
}

