package kai.system.states;

public class BootUpState extends States {

	private void connect2DB() {
		if (!kai.dbConStatus) {
			System.out.println("Connecting to Database");
			System.out.println("User authentication is bypassed for now");
			kai.dbConStatus = true;
		}
	}

	public void next() {
		// Retrieve connected user information
		connect2DB();
		kai.setState(StatesFactory.getState(StatesFactory.CONNECTING_STATE));

		// Update previous State
		prevState = StatesFactory.BOOT_UP_STATE;
	}
}
