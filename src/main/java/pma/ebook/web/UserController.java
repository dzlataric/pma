package pma.ebook.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pma.ebook.items.Item;
import pma.ebook.users.ApplicationUser;
import pma.ebook.users.ApplicationUserRepository;
import pma.ebook.users.User;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final ApplicationUserRepository applicationUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/sign-up")
	public void signUp(@RequestBody final User user) {
		applicationUserRepository.save(
			ApplicationUser.builder().fullName(user.getFullName()).username(user.getUsername()).password(bCryptPasswordEncoder.encode(user.getPassword()))
				.build());
	}

	@GetMapping(value = "/search")
	public List<User> search(@RequestParam final String keyword) {
		return applicationUserRepository.findByKeyword(keyword.toLowerCase()).stream()
			.map(au -> User.builder().id(au.getId()).fullName(au.getFullName()).username(au.getUsername()).build())
			.collect(Collectors.toList());
	}

	@GetMapping("/favorites/{userId}")
	public List<Item> findFavorites(@PathVariable final Long userId) {
		return applicationUserRepository.findById(userId)
			.map(u -> u.getFavoriteItems().stream()
				.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription()).publisher(item.getPublisher()).build())
				.collect(Collectors.toList())).orElseThrow();
	}

	@GetMapping("/to-read/{userId}")
	public List<Item> findToRead(@PathVariable final Long userId) {
		return applicationUserRepository.findById(userId)
			.map(u -> u.getToReadItems().stream()
				.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription()).publisher(item.getPublisher()).build())
				.collect(Collectors.toList())).orElseThrow();
	}

	@GetMapping("/have-read/{userId}")
	public List<Item> findHaveRead(@PathVariable final Long userId) {
		return applicationUserRepository.findById(userId)
			.map(u -> u.getHaveReadItems().stream()
				.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription()).publisher(item.getPublisher()).build())
				.collect(Collectors.toList())).orElseThrow();
	}

}
