package pma.ebook.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
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
			ApplicationUser.builder().fullName(user.getName()).username(user.getUsername()).password(bCryptPasswordEncoder.encode(user.getPassword())).build());
	}
}
