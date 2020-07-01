package pma.ebook.bookstore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Item {

	private Long id;
	private String title;
	private String base64EncodedImage;

}
