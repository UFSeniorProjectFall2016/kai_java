package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ConnectingState extends States {
	private long strt;
	public void execute(Kai kai) {
		printCurrentState();
		
		// Try establishing connection for 5 seconds max
		strt = System.currentTimeMillis();
		System.out.println("Connecting to web ...");
		kai.connectWeb();
		while ( !kai.webConnected() && (System.currentTimeMillis()-strt < 5000) ) {}
		
		System.out.println("Connecting to ros ...");
		kai.connectRos();
		
		System.out.println("Done connecting");
		// Proceed to the receiving state
		Start.state = StatesFactory.getState(StatesFactory.RECEIVING_STATE);
	}
}
