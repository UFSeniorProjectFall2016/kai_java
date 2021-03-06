package kai.system.states;

import kai.system.system.Kai;
import main.java.Start;

public class ReceivingState extends States {
	public void execute(Kai kai) {
//		printCurrentState();
//		System.out.println("Thread before: " + Thread.currentThread().getName());
		try {
			kai.getROSConn().receiveMsg();
			if(Start.ros_msg_flag) {
				String tmp = Start.it.externalDevice().toString();
				System.out.println("ROS message received: " + tmp);
				kai.getWebConn().sendMsg("feedback", tmp);
				Start.clearRosMessage();
			}
		} catch(Exception e) {
			System.out.println("ROS connection error occured while receiving");
			Start.state = StatesFactory.getState(StatesFactory.CONNECTING_STATE);
			return;
		}
		
		Start.state = StatesFactory.getState(StatesFactory.SENDING_STATE);
	}
}