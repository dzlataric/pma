package pma.ebook.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pma.ebook.bookstore.ApplicationUser;
import pma.ebook.bookstore.ApplicationUserRepository;
import pma.ebook.bookstore.Item;
import pma.ebook.bookstore.ItemRepository;
import pma.ebook.bookstore.User;
import pma.ebook.bookstore.UserItem;

@Slf4j
@Transactional
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final ApplicationUserRepository applicationUserRepository;
	private final ItemRepository itemRepository;
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

	@PostMapping("/favorites/add")
	public void addToFavorites(@RequestBody final UserItem userItem) {
		final var user = applicationUserRepository.findById(userItem.getUserId()).orElseThrow();
		user.addFavorite(itemRepository.findById(userItem.getItemId()).orElseThrow());
	}

	@PostMapping("/favorites/remove")
	public void removeFavorite(@RequestBody final UserItem userItem) {
		final var user = applicationUserRepository.findById(userItem.getUserId()).orElseThrow();
		user.removeFavorite(itemRepository.findById(userItem.getItemId()).orElseThrow());
	}

	@GetMapping("/to-read/{userId}")
	public List<Item> findToRead(@PathVariable final Long userId) {
		return applicationUserRepository.findById(userId)
			.map(u -> u.getToReadItems().stream()
				.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription()).publisher(item.getPublisher()).build())
				.collect(Collectors.toList())).orElseThrow();
	}

	@PostMapping("/to-read/add")
	public void addToRead(@RequestBody final UserItem userItem) {
		final var user = applicationUserRepository.findById(userItem.getUserId()).orElseThrow();
		user.addToRead(itemRepository.findById(userItem.getItemId()).orElseThrow());
	}

	@PostMapping("/to-read/remove")
	public void removeToRead(@RequestBody final UserItem userItem) {
		final var user = applicationUserRepository.findById(userItem.getUserId()).orElseThrow();
		user.removeToRead(itemRepository.findById(userItem.getItemId()).orElseThrow());
	}

	@GetMapping("/have-read/{userId}")
	public List<Item> findHaveRead(@PathVariable final Long userId) {
		return applicationUserRepository.findById(userId)
			.map(u -> u.getHaveReadItems().stream()
				.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription()).publisher(item.getPublisher()).build())
				.collect(Collectors.toList())).orElseThrow();
	}

	@PostMapping("/have-read/add")
	public void addHaveRead(@RequestBody final UserItem userItem) {
		final var user = applicationUserRepository.findById(userItem.getUserId()).orElseThrow();
		user.addHaveRead(itemRepository.findById(userItem.getItemId()).orElseThrow());
	}

	@PostMapping("/have-read/remove")
	public void removeHaveRead(@RequestBody final UserItem userItem) {
		final var user = applicationUserRepository.findById(userItem.getUserId()).orElseThrow();
		user.removeHaveRead(itemRepository.findById(userItem.getItemId()).orElseThrow());
	}

}
