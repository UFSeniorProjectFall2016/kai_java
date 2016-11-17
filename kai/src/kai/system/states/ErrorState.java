package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ErrorState extends States {
	private long strt;
	public void execute(Kai kai) {
		printCurrentState();
		kai.getWebConn().sendMsg("ping_res", "0");
		
		// This serves no purpose other than to wait in that state for 1 ms
		strt = System.currentTimeMillis();
		while (System.currentTimeMillis()-strt < 1000 ) {}
		// Done waiting
		Start.state = StatesFactory.getState(StatesFactory.CONNECTING_STATE);
	}
}
