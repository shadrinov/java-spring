package ru.ntechs.insworks.ami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AMI extends Thread {
	@Value("${ami.hostname}")
	private String amiHostname;

	@Value("${ami.port}")
	private Integer amiPort;

	@Value("${ami.username}")
	private String amiUsername;

	@Value("${ami.password}")
	private String amiPassword;

	private Socket amiSocket;
	private PrintWriter out;
	private BufferedReader in;

	private final Logger logger = LoggerFactory.getLogger(AMI.class);

	public AMI() {
		super();

		in = null;
		out = null;

		logger.info(String.format("application.properties: app.title=%s", amiHostname));
	}

	@Override
	public void run() {
		if ((in == null) || (out == null)) {
			try {
				amiLogin();
			} catch (UnknownHostException e) {
				logger.error(String.format("Unable to connect to Asterisk Manager Interface (AMI): %s", e.getLocalizedMessage()));
			} catch (IOException e) {
				logger.error(String.format("I/O with Asterisk Manager Interface (AMI) failed: %s", e.getLocalizedMessage()));
			}
		}
	}

	private void amiLogin() throws UnknownHostException, IOException {
		if (amiHostname == null) {
			logger.error("ami.hostname not defined in application.properties");
			return;
		}

		if (amiPort == null) {
			logger.error("ami.port not defined in application.properties");
			return;
		}

		if (amiUsername == null) {
			logger.error("ami.username not defined in application.properties");
			return;
		}

		if (amiPassword == null) {
			logger.error("ami.password not defined in application.properties");
			return;
		}

		amiSocket = new Socket(amiHostname, amiPort);

		out = new PrintWriter(amiSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(amiSocket.getInputStream()));
	}
}
