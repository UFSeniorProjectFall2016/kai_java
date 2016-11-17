package kai.system.system;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

import main.java.Start;

public class SocketConnection {
	// Class data members
	private String uri;
	private Socket socket;
	private static String message;
	private static boolean msgReceived;
	private static boolean connection_flag = false;
	
	private void connect() {
		try {
			socket = IO.socket(uri);
			this.sendMsg("connected_user", "Java Application");
			socket.on("ping system", new Emitter.Listener() {
				public void call(Object... args) {
					// Find out who made the request
					// Send feed back only if user is approved
					
					connection_flag = true;	// Connection is alive
				}
			}).on("device status", new Emitter.Listener() {

				public void call(Object... args) {
					if(args.length != 0) {
						// Message received
						Start.receiveUserMessage(args[0].toString());
					}
					
					connection_flag = true; // connection is alive
				}
			});
			socket.connect();
			connection_flag = true;
		} catch (URISyntaxException e) {
			System.err.println("SOCKET DID NOT GET CREATED");
			connection_flag = false;
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
			System.err.println("Error will be thrown indicating connectionError");
			connection_flag = false;
		} else {
			uri = webUri;
			this.connect();
		}
	}
	
	public boolean isConnected() {
		return (socket == null) ? false : (connection_flag || socket.connected());
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
