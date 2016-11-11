package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class BootUpState extends States {
	public void execute(Kai kai) {
		printCurrentState();
		
		Start.state = StatesFactory.getState(StatesFactory.CONNECTING_STATE);
	}
}
