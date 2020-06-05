package pma.ebook.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pma.ebook.items.Item;
import pma.ebook.items.ItemRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemsController {

	private final ItemRepository itemRepository;

	@GetMapping
	public List<Item> findAll() {
		return itemRepository.findAll().stream()
			.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).description(item.getDescription()).publisher(item.getPublisher()).build())
			.collect(Collectors.toList());
	}

}
