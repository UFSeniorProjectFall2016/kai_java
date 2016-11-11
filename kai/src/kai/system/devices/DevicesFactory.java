package kai.system.devices;

public class DevicesFactory {
	// Static constant
	public static final int EXT_DEVICE = 1;
	public static final int INT_DEVICE = 2;

	// Class members
	private static Devices external = new ExternalDevice();
	private static Devices internal = new InternalDevice();

	public static ExternalDevice createExtDevice() {
		return (ExternalDevice) external;
	}
	
	public static Devices createDevice(int device_type) {
		Devices res;
		switch (device_type) {
		case EXT_DEVICE:
			res = external;
			break;
		case INT_DEVICE:
			res = internal;
			break;
		default:
			res = null;
			break;
		}
		return res;
	}
}
