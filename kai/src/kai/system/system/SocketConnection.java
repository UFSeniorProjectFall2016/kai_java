package kai.system.system;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

import main.java.Start;

public class SocketConnection {
	// Class data members
	private boolean connectionError = false;
	private String uri;
	private Socket socket;
	private static String message;
	private static boolean msgReceived;
	
	private void connect() {
		try {
			socket = IO.socket(uri);
			this.sendMsg("connected_user", "Java Application");
			socket.on("device status", new Emitter.Listener() {

				public void call(Object... args) {
					if(args.length != 0) {
						// Message received
						Start.receiveUserMessage(args[0].toString());
					}
				}
			});
			socket.connect();
			connectionError = false;
		} catch (URISyntaxException e) {
			System.out.println("SOCKET DID NOT GET CREATED");
			connectionError = true;
		}
	}
	
	public void sendMsg(String msgType, String msgData) {
		if(this.socket != null) {
			socket.emit(msgType, msgData);
		}
	}
	
	// PUBLIC METHODS
	public SocketConnection(String webUri) {
		if(webUri.isEmpty()) {
			System.out.println("Error will be thrown indicating connectionError");
			connectionError = true;
		} else {
			uri = webUri;
			this.connect();
		}
	}
	
	public boolean isConnected() {
		return (socket == null) ? false : (!connectionError && socket.connected());
	}
	
	public static void clearMessage() {
		msgReceived = false;
		message = null;
	}
	
	public static String getMessage() {
		return message;
	}
	
	public static void setMessage(String msg) {
		msgReceived = true;
		message = msg;
	}
	
	public static boolean hasReceivedMsg() {
		return msgReceived;
	}
}
