package pma.ebook.items;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Item {

	private Long id;
	private String title;
	private String description;
	private String publisher;

}
