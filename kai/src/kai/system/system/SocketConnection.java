package kai.system.system;

import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
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
			this.sendMsg("ping_res", "1");
			socket.on("ping_req", new Emitter.Listener() {
				public void call(Object... args) {
					// Find out who made the request
					// Send feed back only if user is approved
					System.out.println("I got one");
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
			}).on("status_req", new Emitter.Listener() {
				public void call(Object... args) {
					// emit every single device status to user
					System.out.println("request all devices");
					
					JSONArray jarr = new JSONArray();
					JSONObject o;
					try {
						for(String s:  DatabaseConnection.getAllDevices()) {
							o = new JSONObject(s);
							jarr.put(o);
						}
					} catch(JSONException e) {
						e.printStackTrace();
					}
					
					sendMsg("status_res", jarr);
					connection_flag = true;	// Connection is alive
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
	
	public void sendMsg(String msgType, Object msgData) {
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
