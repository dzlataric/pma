package pma.ebook.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {


	@GetMapping
	public List<String> dummy() {
		return List.of("dummy", "string");
	}
}
