package ru.ntechs.insworks.ami.responses;

import ru.ntechs.insworks.ami.AMI;
import ru.ntechs.insworks.ami.Response;

public class Success extends Response {
	private String message;

	public Success(AMI ami, String name) {
		super(ami, name);
	}

	@Override
	protected void engage(String attr, String value) {
		super.engage(attr, value);

		if (attr.equalsIgnoreCase("Message"))
			message = value;
		else
			warnUnsupportedAttr(attr, value);
	}

	public String getMessage() {
		return message;
	}
}
