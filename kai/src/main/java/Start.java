package main.java;

import kai.system.devices.ExternalDevice;
import kai.system.states.States;
import kai.system.states.StatesFactory;
import kai.system.system.Kai;

public class Start {
	public static States state;
	public static boolean msg_flag = false;
	public static boolean err_flag = false;
	public static String msg = "";
	
	// Message parsing Variables
	public static ExternalDevice et = new ExternalDevice();

	public static void main(String[] args) {
		// String retrieved from args
		String webUri = "https://sleepy-inlet-14613.herokuapp.com/";
		String rosUri = "localhost";
		int rosPort = 9090;

		// System and the states pattern for the system to move through
		Kai kai = new Kai(webUri, rosUri, rosPort);
		Start.state = StatesFactory.getState(StatesFactory.BOOT_UP_STATE);

		while(true) {
			state.execute(kai);
		}
	}

	public static void receiveMessage(String msg) {
		Start.msg_flag = true;
		Start.msg = msg;
		Start.et.parse(Start.msg);
		Start.state = StatesFactory.getState(StatesFactory.SENDING_STATE);
	}
	
	public static void clearMessage() {
		Start.msg = "";
		Start.msg_flag = false;
	}
}
