package pma.ebook.bookstore;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@JsonInclude(Include.NON_NULL)
public class Settings {

	@Nullable
	private Long id;
	@Nullable
	private Long userId;
	private Long fontSize;
	private Long brightnessLevel;

}
