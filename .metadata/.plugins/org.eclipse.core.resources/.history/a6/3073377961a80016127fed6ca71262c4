package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ReceivingState extends States {
	public void execute(Kai kai) {
		printCurrentState();
//		System.out.println("Thread before: " + Thread.currentThread().getName());
		
		Start.state = StatesFactory.getState(StatesFactory.SENDING_STATE);
	}
}