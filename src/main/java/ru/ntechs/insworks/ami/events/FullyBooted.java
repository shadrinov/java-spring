package ru.ntechs.insworks.ami.events;

import ru.ntechs.insworks.ami.AMI;
import ru.ntechs.insworks.ami.Event;

public class FullyBooted extends Event {
	private String privilege;

	public FullyBooted(AMI ami, String name) {
		super(ami, name);
	}

	@Override
	public void engage(String attr, String value) {
		if (attr.equalsIgnoreCase("Privilege"))
			privilege = value;
		else if (attr.equalsIgnoreCase("Status")) {}
		else
			logger.warn(String.format("Unsupported attribute \"%s\" for message of type \"%s\". Value: \"%s\"", attr, getType(), value));
	}

	public String getPrivilege() {
		return privilege;
	}
}
