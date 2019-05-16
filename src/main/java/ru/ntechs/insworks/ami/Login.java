package ru.ntechs.insworks.ami;

import java.util.ArrayList;

public class Login extends Command {
	private String login;
	private String password;

	public Login(AMI ami, String login, String password) {
		super(ami);

		this.login = login;
		this.password = password;
	}

	@Override
	public Iterable<String> getRequest() {
		ArrayList<String> request = new ArrayList<>();

		request.add("Action: Login");
		request.add(String.format("Username: %s", login));
		request.add(String.format("Secret: %s", password));

		return request;
	}
}
