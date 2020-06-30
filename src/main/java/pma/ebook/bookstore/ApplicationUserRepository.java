package pma.ebook.bookstore;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

	Optional<ApplicationUser> findByUsername(String username);

	@Query("select au from ApplicationUser au where lower(au.fullName) like CONCAT('%', :keyword, '%') or lower(au.username) like CONCAT('%', :keyword, '%')")
	List<ApplicationUser> findByKeyword(@Param("keyword") final String keyword);

}
