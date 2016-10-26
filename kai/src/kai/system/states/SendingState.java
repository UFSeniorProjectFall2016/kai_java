package kai.system.states;

public class SendingState extends States {

	public void next() {
		
		// Try sending message to ROS and if fails, 
		// Set ROS connection false, forward to connecting state
		// Else remain in state until time expired then move to
		// receive State
	}
}