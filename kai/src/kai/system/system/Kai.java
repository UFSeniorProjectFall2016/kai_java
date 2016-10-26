package kai.system.system;

import edu.wpi.rail.jrosbridge.Ros;
import io.socket.client.IO;
import io.socket.client.Socket;
import kai.system.states.States;

public class Kai {
	// Member variables
	private static Ros ros;
	private static Socket socket;
	protected States state;
	
	// Members connections
	private String webHost;
	private String rosHost;
	private int rosPort;
	
	// Status member variables
	public boolean rosConStatus = false;
	public boolean webConStatus = false;
	public boolean dbConStatus = false;
	
	/* ***************************************************
	 * SYSTEM METHODS
	 * ***************************************************/
	// Creating new ROS connection
	public void RosConnection() {
		ros = new Ros(rosHost, rosPort);
	}
	
	// Creating new Socket Connection
	public void SocketConnection() throws Exception {
		socket = IO.socket(webHost);
		socket.emit("connected_user", "Java Application");
	}
	
	// Run the program
	public void run() {
		if(state == null) {
			System.out.println("No states has been set, program cannot run");
		} else {
			// Run program
			state.next();
		}
	}
	
	/* ***************************************************
	 * SETTER METHODS
	 * ***************************************************/
	public void setConnectionInfo(String webHost, String rosHost, int rosPort) {
		this.webHost = webHost;
		this.rosHost = rosHost;
		this.rosPort = rosPort;
	}
	
	public void setState(States state) {
		// Default to boot up
		this.state = state;
	}
	
	/* ***************************************************
	 * GETTER METHODS
	 * ***************************************************/
	public Ros getRos() {
		return ros;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
