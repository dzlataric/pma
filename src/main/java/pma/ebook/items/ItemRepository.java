package pma.ebook.items;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

	@Query("select i from ItemEntity i where lower(i.title) like CONCAT('%', :title, '%')")
	List<ItemEntity> findByTitle(@Param("title") final String title);

}
