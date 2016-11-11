package kai.system.devices;

import java.util.HashMap;

public abstract class Devices implements KaiMessage {
	public static HashMap<String, String>  devices = new HashMap<String, String>();
	
	static {
		devices.put("#light", "L");
		devices.put("#wind", "W");
		devices.put("#coffee", "C");
		devices.put("#door", "D");
		devices.put("#homeCond", "HC");
	};
}
