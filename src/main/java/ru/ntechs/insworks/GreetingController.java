package ru.ntechs.insworks;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.ntechs.insworks.ami.AMI;
import ru.ntechs.insworks.jpa.Model;

@RestController(value="/greeting")
public class GreetingController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	private ModelRepository repository;

	@Autowired
	private AMI ami;

	@RequestMapping(method=RequestMethod.GET)
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
					String.format(template, name));
	}

	@RequestMapping(method=RequestMethod.GET, value="/greetMe")
	public Page<Model> greetMe(Pageable page) {
		return repository.findAll(page);
	}

	@RequestMapping(method=RequestMethod.POST)
	public Greeting create(@RequestBody LoginForm form) {
		return new Greeting(counter.incrementAndGet(),
					String.format(template, form.getUsername()));
	}

	@ModelAttribute
	public void setVaryResponseHeader(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
	}
}

