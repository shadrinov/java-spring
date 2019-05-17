package ru.ntechs.insworks.ami.responses;

import ru.ntechs.insworks.ami.AMI;
import ru.ntechs.insworks.ami.Response;

public class Success extends Response {
	private String message;

	public Success(AMI ami, String name) {
		super(ami, name);
	}

	@Override
	public void engage(String attr, String value) {
		if (attr.equalsIgnoreCase("Message"))
			message = value;
		else
			logger.warn(String.format("Unsupported attribute \"%s\" for message of type \"%s\". Value: \"%s\"", attr, getType(), value));
	}

	public String getMessage() {
		return message;
	}
}
