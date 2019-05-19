package ru.ntechs.insworks.ami;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {
	private final String type;
	private final String name;
	private final AMI ami;

	protected final Logger logger = LoggerFactory.getLogger(Message.class);

	private ArrayList<String> body = new ArrayList<>();

	public Message(AMI ami, String type, String name) {
		super();

		this.ami = ami;
		this.type = type;
		this.name = name;
	}

	public AMI getAMI() {
		return ami;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void dump() {
		logger.warn(String.format("%s: %s", getType(), getName()));

		for (String str : body) {
			logger.warn(str);
		}

		logger.warn("");
	}

	protected void engage(String attr, String value) {
		body.add(String.format("%s: %s", attr, value));
	}

	protected void warnUnsupportedAttr(String attr, String value) {
		logger.warn(String.format("Unsupported attribute \"%s\" for message of type \"%s\". Value: \"%s\"", attr, getType(), value));
	}
}
