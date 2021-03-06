package main.java;

import kai.system.devices.ExternalDevice;
import kai.system.devices.InternalDevice;
import kai.system.states.States;
import kai.system.states.StatesFactory;
import kai.system.system.Kai;

public class Start {
	public static States state;
	public static boolean err_flag = false;
	public static boolean user_msg_flag = false;
	public static String user_msg = "";
	public static boolean ros_msg_flag = false;
	public static String ros_msg = "";
	
	// Message parsing Variables
	public static ExternalDevice et = new ExternalDevice();
	public static InternalDevice it = new InternalDevice();

	public static void main(String[] args) {
		// String retrieved from args
		String webUri = "https://sleepy-inlet-14613.herokuapp.com/";
//		String webUri = "http://192.168.0.102:5000";
		String rosUri = "localhost";
		int rosPort = 9090;

		// System and the states pattern for the system to move through
		Kai kai = new Kai(webUri, rosUri, rosPort);
		Start.state = StatesFactory.getState(StatesFactory.BOOT_UP_STATE);

		while(true) {
			state.execute(kai);
		}
	}

	public static void receiveUserMessage(String msg) {
		Start.et.parse(msg);
//		if(!Start.et.errorGenerated()) {
			Start.user_msg_flag = true;
			Start.user_msg = msg;
			Start.state = StatesFactory.getState(StatesFactory.SENDING_STATE);
//		}
//		Start.clearUserMessage();
	}
	
	public static void receiveRosMessage(String msg) {
		Start.ros_msg_flag = true;
		Start.ros_msg = msg;
		Start.it.parse(msg);
	}
	
	public static void clearUserMessage() {
		Start.user_msg = "";
		Start.user_msg_flag = false;
	}
	
	public static void clearRosMessage() {
		Start.ros_msg = "";
		Start.ros_msg_flag = false;
	}
}
