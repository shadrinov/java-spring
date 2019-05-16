package ru.ntechs.insworks.ami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AMI extends Thread {
	private AMIConfig config;

	private Integer verMajor;
	private Integer verMinor;

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	private Event currentEvent;
	private final Logger logger = LoggerFactory.getLogger(AMI.class);


	public AMI(AMIConfig config) {
		super();

		this.config = config;

		reset();
		start();
	}

	private void reset() {
		this.socket = null;
		this.in = null;
		this.out = null;
		this.currentEvent = null;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String ln;

				logger.info(String.format("connecting to ami://%s:%d...", config.getHostname(), config.getPort()));

				amiConnect(config.getHostname(), config.getPort());
				logger.info(String.format("successfully connected to ami://%s:%d", config.getHostname(), config.getPort()));

				submit(new Login(this, config.getUsername(), config.getPassword()));
				ln = in.readLine();

				final String amiName = "Asterisk Call Manager/";

				if (ln.startsWith("Asterisk Call Manager/")) {
					String version = ln.substring(amiName.length());
					Integer pointPos = version.indexOf('.');

					try {
						verMajor = Integer.decode(version.substring(0, pointPos));
						verMinor = Integer.decode(version.substring(pointPos + 1));

						logger.info(String.format("AMI version: %d.%d", verMajor, verMinor));
					} catch (NumberFormatException e) {
						logger.error(String.format("Unable to parse AMI version: %s", version));
					}
				}

				while (true) {
					ln = in.readLine();

					if (!ln.isEmpty()) {
						logger.info(String.format("got message: %s", ln));
					}
					else
						logger.info("TODO: dispatching event");
				}
			} catch (UnknownHostException e) {
				logger.error(String.format("Unable to connect to Asterisk Manager Interface (AMI): %s", e.getLocalizedMessage()));
			} catch (IOException e) {
				logger.error(String.format("I/O with Asterisk Manager Interface (AMI) failed: %s", e.getLocalizedMessage()));
			} finally {
				reset();

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
			}
		}
	}

	private void amiConnect(String hostname, Integer port) throws UnknownHostException, IOException {
		if (config.getHostname() == null) {
			logger.error("ami.hostname not defined in application.properties");
			return;
		}

		if (config.getPort() == null) {
			logger.error("ami.port not defined in application.properties");
			return;
		}

		if (config.getUsername() == null) {
			logger.error("ami.username not defined in application.properties");
			return;
		}

		if (config.getPassword() == null) {
			logger.error("ami.password not defined in application.properties");
			return;
		}

		socket = new Socket(hostname, port);

		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void submit(Command cmd) {
		for (String str : cmd.getRequest()) {
			logger.info(String.format("sending: %s", str));
			out.println(str);
		}

		logger.info("sending: <LF>");
		out.println();
	}
}
