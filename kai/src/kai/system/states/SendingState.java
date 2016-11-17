package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class SendingState extends States {
	
	public void execute(Kai kai) {
//		printCurrentState();
		
		// Wait for n milliseconds in sending state
		long strt = System.currentTimeMillis();
		while ((System.currentTimeMillis() - strt < 30)) {}
		if(Start.user_msg_flag) {
			System.out.println("Sending " + Start.user_msg + "-> " + Start.et.internalDevice().toString());
			try {
				kai.getROSConn().sendMsg(Start.et.internalDevice().toString());
				kai.getDBConn().saveToDB(Start.et.getDevId(), Start.et.toString());
				Start.clearUserMessage();
			} catch(Exception e) {
				Start.state = StatesFactory.getState(StatesFactory.ERROR_STATE);
				return;
			}
		}
		
		// Go back to receiving state
		Start.state = StatesFactory.getState(StatesFactory.RECEIVING_STATE);
	}
}