package ru.ntechs.insworks.ami;

public abstract class Command {
	private AMI ami;

	public Command(AMI ami) {
		super();
		this.ami = ami;
	}

	public abstract Iterable<String> getRequest();
}
