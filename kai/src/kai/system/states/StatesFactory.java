package kai.system.states;

import kai.system.system.Kai;

public class StatesFactory {
	// public accessible members
	public static final int BOOT_UP_STATE = 0;
	public static final int CONNECTING_STATE = 1;
	public static final int RECEIVING_STATE = 2;
	public static final int SENDING_STATE = 3;
	public static final int ERROR_STATE = 4;

	// Statically define states
	private static States s0 = new BootUpState();
	private static States s1 = new ConnectingState();
	private static States s2;
	private static States s3;
	private static States s4;

	public static void addSystem(Kai kai) {
		s0.setSystem(kai);
		s1.setSystem(kai);
//		s2.setSystem(kai);
//		s3.setSystem(kai);
//		s4.setSystem(kai);
	}
	
	public static States getState(int state) {
		States res;
		switch (state) {
		case BOOT_UP_STATE:
			res = s0;
			break;
		case CONNECTING_STATE:
			res = s1;
			break;
		case RECEIVING_STATE:
			res = s2;
			break;
		case SENDING_STATE:
			res = s3;
			break;
		case ERROR_STATE:
			res = s4;
			break;
		default:
			res = null;
			break;
		}
		return res;
	}
}
