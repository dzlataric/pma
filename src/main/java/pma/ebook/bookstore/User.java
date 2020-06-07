package pma.ebook.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Builder(toBuilder = true)
public class User {

	@Nullable
	private Long id;
	private String fullName;
	private String username;
	@Nullable
	private String password;

}
