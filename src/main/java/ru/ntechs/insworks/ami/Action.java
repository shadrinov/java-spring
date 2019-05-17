package ru.ntechs.insworks.ami;

public abstract class Action extends Message {

	public Action(AMI ami, String name) {
		super(ami, name);
	}

	public abstract Iterable<String> getMessageText();

	@Override
	public String getType() {
		return "Action";
	}

	@Override
	public void engage(String attr, String value) {
		logger.warn(String.format("Useless use of method 'engage': attribute \"%s\" for message of type \"%s\". Value: \"%s\"", attr, getType(), value));
		return;
	}
}
