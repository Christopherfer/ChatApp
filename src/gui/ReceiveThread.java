package gui;

import java.io.IOException;

import net.Client;

public class ReceiveThread extends Thread {
	private Client client;
	private ChatAppWindow chatAppWindow;
	
	public ReceiveThread(Client client, ChatAppWindow chatAppWindow){
		this.client = client;
		this.chatAppWindow = chatAppWindow;
	}
	@Override
	public void run(){
		try{
			while(true){
			String words = client.receiveMessage();
			chatAppWindow.setTextArea(words);
			}
		}catch(IOException e){
				e.printStackTrace();
		}
	}
}

