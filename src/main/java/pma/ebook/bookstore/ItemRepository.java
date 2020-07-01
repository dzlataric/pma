package pma.ebook.bookstore;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

	@Query("select i from ItemEntity i where lower(i.title) like CONCAT('%', :title, '%')")
	List<ItemEntity> findByTitlePartialMatch(@Param("title") String title);

	Optional<ItemEntity> findByTitle(String title);

	boolean existsByTitle(String title);

}
