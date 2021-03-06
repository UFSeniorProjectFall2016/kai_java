package kai.system.devices;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class ExternalDevice extends Devices {
	// device id conversion
	private static HashMap<String, String> ext_2_int_dev = new HashMap<String, String>();
		
	// String Constant comparison
	static final String DEVICE_TYPE = "_type";
	static final String DEVICE_ID = "_id";
	static final String DEVICE_NAME = "_name";
	static final String DEVICE_STATE = "_state";
	static final String DEVICE_STATUS = "_status";
	private int device_type;
	
	// Class data members
	private String _type;
	private String _id;
	private String _name;
	private boolean _state;
	private String _status;
	private String extMsg;
	private boolean error_flag;
	private String err_msg;
	
	private void createMsgString() {
		try {
			JSONObject json = new JSONObject();
			json.put(DEVICE_TYPE, _type);
			json.put(DEVICE_NAME, _name);
			json.put(DEVICE_STATE, _state);
			json.put(DEVICE_STATUS, _status);
			extMsg = json.toString();
		} catch (JSONException e) {
			error_flag = true;
			err_msg = "Error in generating JSON message object";
		}
	}
	
	// Default constructor. Use to create map external devices id
	// to internal devices id
	public ExternalDevice() {
		this.reset();
		for(Entry<String, String> el:devices.entrySet()) {
			String key = el.getKey();
			String value = el.getValue();
			ext_2_int_dev.put(key, value);
		}
	}
	
	public ExternalDevice(int device_type, String _id, String _status) {
		this.reset();
		this.device_type = device_type;
		this._id = _id;
		this._state = true;
		this._status = _status;
		this.createMsgString();
	}
	
	public void parse(String msg) {
		// Expected String
		this.reset();
		try {
			parseJSON( new JSONObject(msg) );
			extMsg = msg;
		} catch (JSONException e) {
			error_flag = true;
			err_msg = "Error generated (External Dev). Invalid String JSON object message";
			System.out.println(err_msg);
		}
	}
	
	public void parseJSON(JSONObject msg) {
		try {
			device_type = msg.getInt("_type");
			_id = msg.getString("_id");
			_name = msg.getString("_name");
			_state = msg.getBoolean("_state");
			_status = msg.getString("_status");
		} catch (JSONException e) {
			// set error in parsing message
			error_flag = true;
			err_msg = "Error generated in parsing JSON message";
			System.out.println(err_msg);
		}
	}
	
	public void reset() {
		device_type = -1;		// Device type not supported
		_type = null;
		_id = null;
		_name = null;
		_state = false;
		_status = null;
		extMsg = null;
		error_flag = false;
		err_msg = null;
	}
	
	public InternalDevice internalDevice() {
		String t = ext_2_int_dev.get(_id);
		return new InternalDevice(device_type, t, _status);
	}
	
	public String toString() {
		return extMsg;
	}

	public boolean errorGenerated() {
		return error_flag;
	}

	public String errorMessage() {
		return err_msg;
	}

}
