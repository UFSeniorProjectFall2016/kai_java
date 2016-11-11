package kai.system.devices;

public class InternalDevice extends Devices {
	
	// Class Members
	private int device_type;
	private char _id;
	private String _status;
	private String intMsg;
	
	public void parse(String msg) {
		
	}
	
	public static String generateDeviceMsg(String _id, boolean status) {
		// This should be replaced by a dictionary to get 
		// the correct ros id from the external client id
		// For now replace with switch statement
		String res = "{\"";
		
		if(_id.equalsIgnoreCase("#light")) {
			res += "L";
		} else if(_id.equalsIgnoreCase("#window")) {
			res += "w";
		}
		
		res += (status) ? "ON" : "OF";
		res += "\"}";
		return res;
	}
	
	public void reset() {
		device_type = -1;		// Device type not supported
		intMsg = null;
	}

	public void createType1Device() {
		device_type = 1;
	}

	public void createType2Device() {
		device_type = 2;
	}

	public String extDevToString() {
		return null;
	}

	public String rosDevToString() {
		return intMsg;
	}
}
