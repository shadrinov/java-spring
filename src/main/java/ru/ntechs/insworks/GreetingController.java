package ru.ntechs.insworks;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;

import ru.ntechs.ami.AMI;
import ru.ntechs.insworks.jpa.Model;

@RestController
@CrossOrigin(origins = "http://polisoteka.zhavoronkov.ntechs")
public class GreetingController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	private ModelRepository repository;

	@Autowired
	private AMI ami;

	@RequestMapping(method=RequestMethod.GET, value="/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
//		ami.addHandler("PeerStatus", message -> {
//			logger.info(String.format("Hey, event accepted: \"%s: %s\"", message.getType(), message.getName()));
//		});
//
//		ami.addHandler("VarSet", message -> {
//			logger.info(String.format("Plain Message: \"%s: %s\"", message.getType(), message.getName()));
//		});

		return new Greeting(counter.incrementAndGet(),
					String.format(template, name));
	}

	@RequestMapping(method=RequestMethod.GET, value="/greetMe")
	public Page<Model> greetMe(Pageable page) {
		return repository.findAll(page);
	}

//	@RequestMapping(method=RequestMethod.POST, value="/greeting")
//	public Greeting create() {
//		return new Greeting(counter.incrementAndGet(),
//					String.format(template, form.getUsername()));
//	}

	@SubscribeMapping("/topic/news")
	public Collection<Greeting> retrieveParticipants() {
//		return participantRepository.getActiveSessions().values();
		return null;
	}

	@MessageMapping("/news.test")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting(1, "Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@EventListener(SessionConnectEvent.class)
	public void handleWebsocketConnectListner(SessionConnectEvent event) {
		logger.info("Received a new web socket connection");
	}

	@EventListener(SessionDisconnectEvent.class)
	public void handleWebsocketDisconnectListner(SessionDisconnectEvent event) {
		logger.info("session closed");
	}

	@ModelAttribute
	public void setVaryResponseHeader(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
	}

	public AMI getAmi() {
		return ami;
	}
}
