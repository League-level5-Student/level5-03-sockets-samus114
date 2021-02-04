package _02_Chat_Application;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	private String ip;
	private int port;
	String string = "<html>";
	Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void start() {
		ChatApp.label.setText(string);
		try {
			connection = new Socket(ip, port);
			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (connection.isConnected()) {
			try {
				ChatApp.label.setText(ChatApp.label.getText() + "<br/>" + (String) is.readObject());
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(null, "connection lost");
				System.exit(0);
			}
		}
	}

	public void sendMessage() {
		string = ChatApp.textbox.getText();
		try {
			if (os != null) {
				os.writeObject(string);
				os.flush();
			}
			ChatApp.textbox.setText("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}