package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ConnectingState extends States {
	public void execute(Kai kai) {
		printCurrentState();
		
		// Try establishing connection to webclient
		if(kai.webConnected()) {
			System.out.println("\tConnected to Web!");
		} else {
			System.out.println("\tConnecting to web ...");
			kai.connectWeb();
		}
		
		
		if(kai.rosConnected()) {
			System.out.println("\tConnected to ROS!");
		} else {
			System.out.println("\tConnecting to ros ...");
			if(Kai.updateConnectionStatusMsg(Kai.stdMsg, 1, "Kai system is connecting")) {
				kai.getWebConn().sendMsg("ping_res", Kai.stdMsg);
			}
			kai.connectRos();
		}
		
		if(!kai.rosConnected()) {
			Start.state = StatesFactory.getState(StatesFactory.ERROR_STATE);
			return;
		}
		
		System.out.println("\tConnection complete");
		// Proceed to the receiving state
		if(Kai.updateConnectionStatusMsg(Kai.stdMsg, 2, "Kai system is online")) {
			kai.getWebConn().sendMsg("ping_res", Kai.stdMsg);
		}
		Start.state = StatesFactory.getState(StatesFactory.RECEIVING_STATE);
	}
}
