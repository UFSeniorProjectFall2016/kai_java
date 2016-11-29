package kai.system.system;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Kai {
	// Class Members definition
	private String webUri;
	private String rosUri;
	private String dbUri;
	private int rosPort;
	private static JSONObject connMsg;
	public static JSONObject stdMsg;
	public static int lstStat = -1;

	RosConnection rosConn;
	SocketConnection webConn;
	DatabaseConnection dbConn;

	private void createStdMsg() {
		stdMsg = new JSONObject();
		try {
			stdMsg.append("origin", "Kai");
			stdMsg.append("date", new Date());
			stdMsg.append("data", connMsg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Kai(String webUri, String rosUri, int rosPort, String dbUri) {
		this.webUri = webUri;
		this.rosUri = rosUri;
		this.rosPort = rosPort;
		this.dbUri = dbUri;
		standardConnectionMsg();
		this.createStdMsg();
	}

	public void connect() {
		this.connectWeb();
		this.connectRos();
	}

	public void connectWeb() {
		// Create a web connection
		// Wait for connection to set or 5ms
		webConn = new SocketConnection(webUri);
		long strt = System.currentTimeMillis();
		while ( !this.webConnected() && (System.currentTimeMillis()-strt < 5000) ) {}
	}

	public void connectRos() {
		if (!this.rosConnected()) {
			rosConn = new RosConnection(rosUri, rosPort);
		}
	}

	public void connectDB() {
		dbConn = new DatabaseConnection(dbUri);
		this.dbUri = dbConn.getDBUri();
	}

	public RosConnection getROSConn() {
		return rosConn;
	}

	public SocketConnection getWebConn() {
		return webConn;
	}

	public DatabaseConnection getDBConn() {
		return dbConn;
	}

	public boolean webConnected() {
		return (webConn == null) ? false : webConn.isConnected();
	}

	public boolean rosConnected() {
		return (rosConn == null) ? false : rosConn.isConnected();
	}
	
	public static JSONObject standardConnectionMsg() {
		if(connMsg == null) {
			connMsg = new JSONObject();
			try {
				connMsg.append("status", null);
				connMsg.append("msg", null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return connMsg;
	}
	
	public static boolean updateConnectionStatusMsg(JSONObject msgConv, int status, String msg) {
		standardConnectionMsg();
		try {
			connMsg.put("status", status);
			connMsg.put("statusChg", !(Kai.lstStat==status));
			connMsg.put("msg", msg);
			msgConv.put("data", connMsg);
			msgConv.put("date", new Date());
			Kai.lstStat = status;
		} catch (JSONException e) {
			return false;
		}
		return true;
	}
}
