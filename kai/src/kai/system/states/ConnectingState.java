package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ConnectingState extends States {
	private long strt;
	public void execute(Kai kai) {
		printCurrentState();
		// Ensure that both web and ROS is connected
		if( !kai.rosConnected() ) {
			// Connect the system to ROS
		}
		
		// Try establishing connection for 5 seconds max
		strt = System.currentTimeMillis();
		System.out.println("Connecting to web ...");
		kai.connect();
		while ( !kai.webConnected() && (System.currentTimeMillis()-strt < 5000) ) {}
		
		// Proceed to the receiving state
		Start.state = StatesFactory.getState(StatesFactory.RECEIVING_STATE);
	}
}
