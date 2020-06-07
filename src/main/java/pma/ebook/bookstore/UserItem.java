package pma.ebook.bookstore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserItem {

	private Long userId;
	private Long itemId;

}
