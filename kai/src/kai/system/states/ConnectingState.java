package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ConnectingState extends States {
	public void execute(Kai kai) {
		printCurrentState();
		
		// Try establishing connection to webclient
		if(kai.webConnected()) {
			System.out.println("\tConnected to Web!");
			kai.getWebConn().sendMsg("ping_res", "1");
		} else {
			System.out.println("\tConnecting to web ...");
			kai.connectWeb();
		}
		
		if(kai.rosConnected()) {
			System.out.println("\tConnected to ROS!");
		} else {
			System.out.println("\tConnecting to ros ...");
			kai.connectRos();
		}
		
		if(!kai.rosConnected()) {
			Start.state = StatesFactory.getState(StatesFactory.ERROR_STATE);
			return;
		}
		
		System.out.println("\tConnection complete");
		// Proceed to the receiving state
		Start.state = StatesFactory.getState(StatesFactory.RECEIVING_STATE);
	}
}
