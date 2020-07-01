package pma.ebook.web;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pma.ebook.bookstore.Item;
import pma.ebook.bookstore.ItemEntity;
import pma.ebook.bookstore.ItemRepository;

@Slf4j
@Transactional
@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemsController {

	private final ItemRepository itemRepository;

	@GetMapping
	public List<Item> findAll() {
		return itemRepository.findAll().stream()
			.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).base64EncodedImage(Base64.getEncoder().encodeToString(item.getImage())).build())
			.collect(Collectors.toList());
	}

	@GetMapping(value = "/search")
	public List<Item> findAll(@RequestParam final String title) {
		return itemRepository.findByTitlePartialMatch(title.toLowerCase()).stream()
			.map(item -> Item.builder().id(item.getId()).title(item.getTitle()).base64EncodedImage(Base64.getEncoder().encodeToString(item.getImage())).build())
			.collect(Collectors.toList());
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> getContent(@RequestParam final String title) {
		final var bytes = itemRepository.findByTitle(title).map(ItemEntity::getContent).orElseThrow();
		log.info("Downloading " + title + " with length: " + bytes.length);
		final var resource = new ByteArrayResource(bytes);

		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + title + ".epub");

		return ResponseEntity.ok()
			.headers(headers)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.body(resource);
	}

}
