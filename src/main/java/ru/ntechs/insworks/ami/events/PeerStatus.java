package ru.ntechs.insworks.ami.events;

import ru.ntechs.insworks.ami.AMI;
import ru.ntechs.insworks.ami.Event;

public class PeerStatus extends Event {
	private String privilege;
	private String channelType;
	private String peer;
	private String peerStatus;
	private String address;

	public PeerStatus(AMI ami, String name) {
		super(ami, name);
	}

	@Override
	public void engage(String attr, String value) {
		if (attr.equalsIgnoreCase("Privilege"))
			privilege = value;
		else if (attr.equalsIgnoreCase("ChannelType"))
			channelType = value;
		else if (attr.equalsIgnoreCase("Peer"))
			peer = value;
		else if (attr.equalsIgnoreCase("PeerStatus"))
			peerStatus = value;
		else if (attr.equalsIgnoreCase("Address"))
			address = value;
		else
			logger.warn(String.format("Unsupported attribute \"%s\" for message of type \"%s\". Value: \"%s\"", attr, getType(), value));
	}

	public String getPrivilege() {
		return privilege;
	}

	public String getChannelType() {
		return channelType;
	}

	public String getPeer() {
		return peer;
	}

	public String getPeerStatus() {
		return peerStatus;
	}

	public String getAddress() {
		return address;
	}
}
