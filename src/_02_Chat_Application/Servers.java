package _02_Chat_Application;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Servers {
	private int port;
	String string;
	private ServerSocket server;
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;

	public Servers(int port) {
		this.port = port;
	}

	public void start() {
		try {
			server = new ServerSocket(port, 100);
			connection = server.accept();
			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());
			os.flush();
			while (connection.isConnected()) {
				try {
					ChatApp.label.setText((String) is.readObject());
					System.out.println(is.readObject());
				} catch (EOFException e) {
					JOptionPane.showMessageDialog(null, "connection lost");
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}

	public int getPort() {
		return port;
	}

	public void sendMessage() {
		string = ChatApp.textbox.toString();
		try {
			if (os != null) {
				os.writeObject(string);
				os.flush();
			}
		} catch (IOException e) {

		}
	}
}