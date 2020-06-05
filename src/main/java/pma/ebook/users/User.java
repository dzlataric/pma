package pma.ebook.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {

	private String fullName;
	private String username;
	private String password;

}
