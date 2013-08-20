package gui;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import net.*;

//WindowにあたるJFrameクラスを継承
public class ChatAppWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JTextArea revArea;
	private Client client;
	private JTextField portField;
	private JTextField IPaddressField;
	private ReceiveThread receiveThread;
	private ChatAppWindow chatAppWindow = this;
	private JButton btnNewButton;
	// 改行コード
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public ChatAppWindow() throws IOException {

		// client = Client.connectAsGuest("127.0.0.1", 10000);テスト用
		// Windowサイズと、閉じるボタン押下時の処理を追加
		this.setSize(705, 546);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		revArea = new JTextArea();
		JScrollPane jScrollPane = new JScrollPane(revArea);
		jScrollPane.setBounds(0, 30, 689, 330);
		revArea.setEditable(false);
		revArea.setLineWrap(true);
		getContentPane().add(jScrollPane);

		textArea = new JTextArea();

		JScrollPane jScrollPane2 = new JScrollPane(textArea);
		jScrollPane2.setBounds(0, 381, 687, 128);
		textArea.setLineWrap(true);
		textArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char key = e.getKeyChar();

				if (key == '\n') {
					String words = textArea.getText();
					textArea.setText("");
					String sendMess = "あなた: ";
					revArea.append(sendMess + words + LINE_SEPARATOR);

					client.sendMessage(words);
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//				int key = e.getKeyCode();
//				if (key == KeyEvent.VK_ENTER) {
//					int mod = e.getModifiersEx();
//					if (((mod & InputEvent.SHIFT_DOWN_MASK) != 0)) {
//						textArea.append(LINE_SEPARATOR);
//					}
//
//				}
			}
		});
		getContentPane().add(jScrollPane2);

		btnNewButton = new JButton("送信");
		btnNewButton.setBounds(0, 360, 689, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String words = textArea.getText();
				textArea.setText("");
				String sendMess = "あなた: ";
				revArea.append(sendMess + words + LINE_SEPARATOR);

				client.sendMessage(words);

			}
		});
		getContentPane().add(btnNewButton);

		portField = new JTextField();
		portField.setBounds(591, 4, 96, 19);
		getContentPane().add(portField);
		portField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("ポート番号");
		lblNewLabel_1.setBounds(506, 7, 79, 13);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("IPアドレス");
		lblNewLabel_2.setBounds(259, 7, 73, 13);
		getContentPane().add(lblNewLabel_2);

		JButton btnNewButton_1 = new JButton("コネクト");

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String IP = IPaddressField.getText();
					int port = Integer.parseInt(portField.getText());
					client = Client.connectAsGuest(IP, port);
					receiveThread = new ReceiveThread(client, chatAppWindow);
					receiveThread.start();
					revArea.append("接続先： " + client.getAddress()
							+ LINE_SEPARATOR);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(29, 3, 96, 21);
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("サーバー");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int port = Integer.parseInt(portField.getText());
					client = Client.connectAsHost(port);
					receiveThread = new ReceiveThread(client, chatAppWindow);
					receiveThread.start();
					revArea.append("接続先: " + client.getAddress()
							+ LINE_SEPARATOR);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(151, 3, 96, 21);
		getContentPane().add(btnNewButton_2);

		IPaddressField = new JTextField();
		IPaddressField.setBounds(336, 4, 158, 19);
		getContentPane().add(IPaddressField);
		IPaddressField.setColumns(10);
	}

	public void setTextArea(String words) {
		String recMess = "あいて: ";
		revArea.append(recMess + words + LINE_SEPARATOR);

	}
}