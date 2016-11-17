package kai.system.system;

public class Kai {
	// Class Members definition
	private String webUri;
	private String rosUri;
	private String dbUri;
	private int rosPort;

	RosConnection rosConn;
	SocketConnection webConn;
	DatabaseConnection dbConn;

	public Kai(String webUri, String rosUri, int rosPort, String dbUri) {
		this.webUri = webUri;
		this.rosUri = rosUri;
		this.rosPort = rosPort;
		this.dbUri = dbUri;
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
}
