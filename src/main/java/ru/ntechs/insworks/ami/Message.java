package ru.ntechs.insworks.ami;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Message {
	private final String name;
	private final AMI ami;

	protected final Logger logger = LoggerFactory.getLogger(Message.class);

	public Message(AMI ami, String name) {
		super();

		this.ami = ami;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public AMI getAMI() {
		return ami;
	}

	public abstract String getType();
	public abstract void engage(String attr, String value);
}
