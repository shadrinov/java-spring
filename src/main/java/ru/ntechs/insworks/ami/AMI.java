package ru.ntechs.insworks.ami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class AMI {
	private Socket amiSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		amiSocket = new Socket(ip, port);

		out = new PrintWriter(amiSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(amiSocket.getInputStream()));
	}
}
