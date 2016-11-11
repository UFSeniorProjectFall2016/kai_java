package kai.system.devices;

import org.json.JSONException;
import org.json.JSONObject;

public class ExternalDevice extends Devices {
	
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
	private boolean _status;
	private String extMsg;
	private boolean error_flag;
	private String err_msg;
	
	public void parse(String msg) {
		// Expected String
		// {id: "some id", name: "some name", status: "true or false"}
		try {
			parseJSON( new JSONObject(msg) );
		} catch (JSONException e) {
			error_flag = true;
			err_msg = "Error generated (External Dev). Invalid String JSON object message";
			System.out.println(err_msg);
		}
	}
	
	public void parseJSON(JSONObject msg) {
		reset();
		try {
			_id = msg.getString("id");
//			_name = msg.getString("name");
			_status = msg.getBoolean("status");
		} catch (JSONException e) {
			// set error in parsing message
			error_flag = true;
			err_msg = "Error generated in parsing JSON message";
			System.out.println(err_msg);
		}
	}
	
	public void createType1Device() {
		reset();
		_type = "1";
		_state = true;			// Device is online if true
		_status = false;
	}
	
	public void createType2Device() {
		reset();
		_type = "2";
		_state = true;			// Device is online if true
		_status = false;
	}
	
	public void reset() {
		device_type = -1;		// Device type not supported
		_type = null;
		_id = null;
		_name = null;
		_state = false;
		_status = false;
		extMsg = null;
		error_flag = false;
		err_msg = null;
	}
	
	public boolean error() {
		return error_flag;
	}
	
	public String getErrorMsg() {
		return err_msg;
	}
	
	public String extDevToString() {
		return extMsg;
	}

	public String rosDevToString() {
		return InternalDevice.generateDeviceMsg(_id, _status);
	}

}
