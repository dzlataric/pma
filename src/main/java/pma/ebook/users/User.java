package pma.ebook.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {

	private String name;
	private String username;
	private String password;

}
