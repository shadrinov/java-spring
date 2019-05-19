package ru.ntechs.insworks.ami;

@FunctionalInterface
public interface EventHandler {
	public void run(Message message);
}
