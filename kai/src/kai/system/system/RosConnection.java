package kai.system.system;

import javax.json.Json;
import javax.json.JsonObject;

import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.callback.TopicCallback;
import edu.wpi.rail.jrosbridge.messages.Message;
import main.java.Start;

public class RosConnection {
	// Class Members
	private String uri;
	private int port;
	private Ros ros;
	private Topic sendTopic;
	private Topic receiveTopic;
	
	private void connect() {
		this.ros = new Ros(uri, port);
		this.ros.connect();
	}
	
	private Topic createTopic(String topicName, String msgType) {
		return new Topic(this.ros, topicName, msgType); 
	}
	
	public RosConnection(String rosUri, int rosPort) {
		this.uri = rosUri;
		this.port = rosPort;
		this.connect();
		this.setSendTopic("/Config_vals", "std_msgs/String");
		this.setReceiveTopic("tempHum", "std_msgs/String");
	}
	
	public void setSendTopic(String topicName, String msgType) {
		this.sendTopic = this.createTopic(topicName, msgType);
	}
	
	public void setReceiveTopic(String topicName, String msgType) {
		this.receiveTopic = this.createTopic(topicName, msgType);
	}
	
	private Message createMessage(String msg) {
		JsonObject msgJSON = Json.createObjectBuilder().add("data", msg).build();
		return new Message(msgJSON);
	}
	
	public boolean sendMsg(Message msg) {
		// Message cannot be sent if topic fails
		if(sendTopic == null) { return false; }
		sendTopic.publish(msg);
		return true;
	}
	
	public boolean sendMsg(String msg) {
		// Message cannot be sent if message is empty
		if(msg.isEmpty()) { return false; }
		return this.sendMsg(this.createMessage(msg));
	}
	
	public void receiveMsg() {
		this.receiveTopic.subscribe(new TopicCallback() {
			public void handleMessage(Message message) {
				Start.clearRosMessage();
				Start.receiveRosMessage(message.clone().toString());
	        }
		});
	}
}
