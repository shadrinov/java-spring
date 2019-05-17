package ru.ntechs.insworks.ami;

public class UnsupportedMessage extends Message {
	private final String type;

	public UnsupportedMessage(AMI ami, String type, String name) {
		super(ami, name);
		this.type = type;

		logger.warn(String.format("Unsupported message \"%s: %s\"", type, name));
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void engage(String attr, String value) {
		return;
	}

}
