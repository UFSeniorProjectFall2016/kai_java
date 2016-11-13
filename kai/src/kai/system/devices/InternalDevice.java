package kai.system.devices;

import java.util.HashMap;
import java.util.Map.Entry;

public class InternalDevice extends Devices {
	// device id conversion
	private static HashMap<String, String> int_2_ext_dev = new HashMap<String, String>();

	// Class Members
	private int device_type;
	private String _id;
	private String _status;
	private String intMsg;
	private boolean error_flag;
	private String err_msg;

	// Default constructor. Use to create map internal devices id
	// to external devices id
	public InternalDevice() {
		this.reset();
		for (Entry<String, String> el : devices.entrySet()) {
			String key = el.getValue();
			String value = el.getKey();
			int_2_ext_dev.put(key, value);
		}
	}

	public InternalDevice(int device_type, String _id, String _status) {
		this.reset();
		this.device_type = device_type;
		this._id = _id;
		this._status = _status;
		this.createMsgString();
	}

	public void parse(String msg) {
		reset();
		if (msg.isEmpty()) {
			// Error, Empty message
			error_flag = true;
			err_msg = "Empty message";
			return;
		}

		// Parse message received from ROS
		intMsg = msg;

	}

	private void createMsgString() {
		if (device_type == 1) {
			String stat = (Boolean.parseBoolean(_status)) ? "ON" : "OF";
			intMsg = "{\"" + _id + stat + "\"}";
		} else if (device_type == 2) {
			intMsg = "{\"" + _status + "\"}";
		}
	}

	public void reset() {
		device_type = -1; // Device type not supported
		error_flag = false;
		intMsg = null;
		err_msg = null;
	}

	public ExternalDevice externalDevice() {
		return new ExternalDevice(device_type, _id, _status);
	}
	
	public String toString() {
		return intMsg;
	}

	public boolean errorGenerated() {
		return error_flag;
	}

	public String errorMessage() {
		return err_msg;
	}
}
