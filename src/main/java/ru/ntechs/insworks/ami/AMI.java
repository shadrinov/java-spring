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

import ru.ntechs.insworks.ami.actions.Login;
import ru.ntechs.insworks.ami.events.FullyBooted;
import ru.ntechs.insworks.ami.events.PeerStatus;
import ru.ntechs.insworks.ami.responses.Success;

@Component
public class AMI extends Thread {
	private Config config;

	private Integer verMajor;
	private Integer verMinor;

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	private Message message;
	private final Logger logger = LoggerFactory.getLogger(AMI.class);


	public AMI(Config config) {
		super();

		this.config = config;

		setName("ami");
		reset();
		start();
	}

	private void reset() {
		this.socket = null;
		this.in = null;
		this.out = null;
		this.message = null;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String ln;

				logger.info(String.format("connecting to ami://%s:%d...", config.getHostname(), config.getPort()));

				amiConnect(config.getHostname(), config.getPort());

				submit(new Login(this, config.getUsername(), config.getPassword()));
				ln = in.readLine();

				final String amiName = "Asterisk Call Manager/";

				if (ln.startsWith("Asterisk Call Manager/")) {
					String version = ln.substring(amiName.length());
					Integer pointPos = version.indexOf('.');

					try {
						verMajor = Integer.decode(version.substring(0, pointPos));
						verMinor = Integer.decode(version.substring(pointPos + 1));

						logger.info(String.format("successfully connected to ami://%s:%d, protocol version: %d.%d", config.getHostname(), config.getPort(), verMajor, verMinor));
					} catch (NumberFormatException e) {
						logger.info(String.format("successfully connected to ami://%s:%d", config.getHostname(), config.getPort(), verMajor, verMinor));
						logger.error(String.format("Failed to parse AMI version: %s", version));
					}
				}

				while ((ln = in.readLine()) != null) {
					if (!ln.isEmpty()) {
						Integer pos = ln.indexOf(':');
						String attr = ln.substring(0, pos).trim();
						String value = ln.substring(pos + 1).trim();

//						logger.info(String.format("parsed line: \"%s\": \"%s\"", attr, value));

						if (message == null) {
							if (attr.equalsIgnoreCase("Event")) {
								if (value.equalsIgnoreCase("PeerStatus"))
									message = new PeerStatus(this, value);
								else if (value.equalsIgnoreCase("FullyBooted"))
									message = new FullyBooted(this, value);
							}
							else if (attr.equalsIgnoreCase("Response")) {
								if (value.equalsIgnoreCase("Success"))
									message = new Success(this, value);
							}

							if (message == null)
								message = new UnsupportedMessage(this, attr, value);
						}
						else
							message.engage(attr, value);
					}
					else {
						logger.info(String.format("TODO: dispatching message \"%s: %s\"", message.getType(), message.getName()));

						message = null;
					}
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

	public void submit(Action cmd) {
		for (String str : cmd.getMessageText()) {
			logger.info(String.format("sending: %s", str));
			out.println(str);
		}

		logger.info("sending: <LF>");
		out.println();
	}
}
