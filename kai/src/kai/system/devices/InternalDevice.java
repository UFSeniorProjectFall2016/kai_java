package kai.system.devices;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

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
		try {
			parseJSON( new JSONObject(msg) );
		} catch (JSONException e) {
			error_flag = true;
			err_msg = "Error generated (Internal Dev). Invalid String JSON object message";
			System.out.println(err_msg);
		}

	}
	
	private void parseJSON(JSONObject msg) {
		try {
			device_type = 3;
			_id = "HC";
			String tmp = msg.getString("data");
			if(tmp.length() >= 2) {
				JSONObject json = new JSONObject();
				json.put("H", (int)tmp.charAt(0));
				json.put("T", (int)tmp.charAt(1));
				this._status = json.toString();
			}
		} catch (JSONException e) {
			// set error in parsing message
			error_flag = true;
			err_msg = "Error generated in parsing JSON message";
			System.out.println(err_msg);
		}
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
		String _newId = int_2_ext_dev.get(this._id);
		return new ExternalDevice(device_type, _newId, _status);
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
