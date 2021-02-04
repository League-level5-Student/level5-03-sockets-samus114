package _02_Chat_Application;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _00_Click_Chat.networking.Server;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	JPanel panel = new JPanel();
	JFrame frame = new JFrame();
	static JLabel label = new JLabel();
	static JTextField textbox = new JTextField(50);
	JButton button = new JButton("Send message?");
	Servers server;
	Client client;

	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp() {
		frame.add(panel);
		panel.add(label);
		panel.add(textbox);
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a chat session?", "Bookface", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION) {
			server = new Servers(8080);
			frame.setTitle("Chat host");
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIpAddress() + "\nPort: " + server.getPort());
			frame.setVisible(true);
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel.add(button);
			button.addActionListener((e) -> {
				server.sendMessage();
			});
			server.start();
		} else {
			frame.add(panel);
			panel.add(label);
			panel.add(textbox);
			panel.add(button);
			frame.setTitle("Chat extra");
			String ipStr = JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			client = new Client(ipStr, port);
			frame.setVisible(true);
			frame.setSize(400, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			button.addActionListener((e) -> {
				client.sendMessage();
			});
			client.start();
		}
	}
}