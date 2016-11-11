package kai.system.devices;

public interface KaiMessage {
	public abstract void parse(String msg);
	public abstract void reset();
	public abstract String toString();
	public abstract boolean errorGenerated();
	public abstract String errorMessage();
}
