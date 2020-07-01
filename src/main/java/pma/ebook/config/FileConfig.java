package pma.ebook.config;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pma.ebook.bookstore.ItemEntity;
import pma.ebook.bookstore.ItemRepository;

@Slf4j
@Configuration
@AllArgsConstructor
@Transactional
public class FileConfig {

	private final ResourceLoader resourceLoader;
	private final ItemRepository itemRepository;

	Resource[] loadResources(final String pattern) throws IOException {
		return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
	}

	@PostConstruct
	public void load() throws IOException {
		final var resources = loadResources("classpath*:/files/*.epub");
		log.info("Found {} resources", resources.length);
		Arrays.stream(resources).forEach(r -> {
			final EpubReader epubReader = new EpubReader();
			try {
				final var inputStream = r.getInputStream();
				final var byteArray = IOUtils.toByteArray(inputStream);
				final Book book = epubReader.readEpub(r.getInputStream());
				final var exists = itemRepository.existsByTitle(book.getTitle());
				if (!exists) {
					itemRepository.save(ItemEntity.builder().title(book.getTitle()).content(byteArray).image(book.getCoverImage().getData()).build());
				}
			} catch (final IOException e) {
				log.error("Error storing file {} to database", r.getFilename(), e);
			}
		});
	}
}
