package kai.system.states;

public class ConnectingState extends States {
	private void connect2Ros() {
		if (!kai.rosConStatus) {
			System.out.println("Connecting to ROS");
			kai.RosConnection();
			if (kai.getRos().isConnected()) {
				kai.rosConStatus = true;
			} else {
				kai.rosConStatus = false;
			}
		}
	}

	private void connect2Web() {
		long strt = 0;
		if (!kai.webConStatus) {
			try {
				kai.SocketConnection();
				kai.getSocket().connect();
				strt = System.currentTimeMillis();
			} catch (Exception e) {
				kai.rosConStatus = false;
				return;
			}

			// Wait for 5 seconds (5000 ms) for connection OR
			// connection successfully established
			while ( (System.currentTimeMillis() - strt < 5000) && !kai.webConStatus) {
				System.out.println("Connecting to Web");
				kai.webConStatus = kai.getSocket().connected();
			}

			if (kai.getSocket().connected()) {
				System.out.println("Connection successful");
				kai.webConStatus = true;
			} else {
				System.out.println("Connection failed");
				kai.webConStatus = false;
			}
		}
	}

	public void next() {
		// User authentication failed
		if (!kai.dbConStatus) {
			kai.setState(StatesFactory.getState(StatesFactory.ERROR_STATE));
		} else {
			connect2Web();
			// connect2Ros();
			if (kai.rosConStatus && kai.webConStatus) {
				// Connection Error resulted from sending state
				if(prevState == StatesFactory.SENDING_STATE) {
					kai.setState(StatesFactory.getState(StatesFactory.SENDING_STATE));
				}
				// Connection Error resulted from receiving state
				else {
					kai.setState(StatesFactory.getState(StatesFactory.RECEIVING_STATE));
				}
			} else {
				kai.setState(StatesFactory.getState(StatesFactory.CONNECTING_STATE));
			}
		}
		
		// Update previous State
		prevState = StatesFactory.CONNECTING_STATE;
	}
}
