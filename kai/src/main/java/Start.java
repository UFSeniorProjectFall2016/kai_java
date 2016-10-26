package main.java;

import java.util.Scanner;

import kai.system.states.StatesFactory;
import kai.system.system.Kai;

public class Start {
	public static void main(String[] args) {
		// Assume that the strings will be given through terminal
		// State default to boot up boot up
		
		// Make change before deployment
		// dbHost  = args[0]
		// webHost = args[1]
		// rosHost = args[2]
		// rosPort = args[3]
		
		// The following strings are for testing purposes
		String webHost = "https://sleepy-inlet-14613.herokuapp.com/";
		String rosHost = "locahost";
		int rosPort = 9090;
		
		// Setting program
		Kai kai = new Kai();
		StatesFactory.addSystem(kai);
		kai.setConnectionInfo(webHost, rosHost, rosPort);
		kai.setState(StatesFactory.getState(StatesFactory.BOOT_UP_STATE));
		
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		do {
			System.out.print("Type 1 to rerun program: ");
			choice = sc.nextInt();
			kai.run();
		} while(choice == 1);
	}
}
