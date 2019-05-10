package ru.ntechs.insworks;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.ntechs.insworks.jpa.Model;

@RestController(value="/greeting")
public class GreetingController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ModelRepository repository;

	@RequestMapping(method=RequestMethod.GET)
	@CrossOrigin("http://polisonline.zhavoronkov.ntechs")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
					String.format(template, name));
	}

	@RequestMapping(method=RequestMethod.GET, value="/greetMe")
	public Page<Model> greetMe(Pageable page) {
		List<Model> t = repository.findByTitle("Cygnet");
		Logger log = Logger.getLogger("test");

		log.info(String.format("Count: %d", t.size()));

		return repository.findAll(page);
	}

	@RequestMapping(method=RequestMethod.POST)
	@CrossOrigin("http://polisonline.zhavoronkov.ntechs")
	public Greeting create(@RequestBody LoginForm form) {
		return new Greeting(counter.incrementAndGet(),
					String.format(template, form.getUsername()));
	}

	@ModelAttribute
	public void setVaryResponseHeader(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
	}
}

