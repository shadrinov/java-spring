package ru.ntechs.insworks.ami;

public abstract class Event extends Message {

	public Event(AMI ami, String name) {
		super(ami, "Event", name);
	}
}
