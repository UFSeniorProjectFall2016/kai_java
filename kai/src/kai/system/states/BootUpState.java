package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class BootUpState extends States {
	public void execute(Kai kai) {
		printCurrentState();
		System.out.println("\tConnecting to local Database");
		kai.connectDB();
		if(kai.getDBConn().numDataAvailable() != 0) {
			Start.sys_con_msgs = kai.getDBConn().getDBData();
			Start.sys_con_msg_flag = true;
		} else {
			Start.sys_con_msg_flag = false;
		}
		Start.state = StatesFactory.getState(StatesFactory.CONNECTING_STATE);
	}
}
