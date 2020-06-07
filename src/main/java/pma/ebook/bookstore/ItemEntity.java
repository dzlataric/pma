package pma.ebook.bookstore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ITEM")
public class ItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private String publisher;

	private byte[] file;

	@ManyToOne(fetch = FetchType.LAZY)
	private ItemCollection collection;

	@ManyToMany(mappedBy = "favoriteItems")
	private Set<ApplicationUser> favoriteUsers = new HashSet<>();

	@ManyToMany(mappedBy = "haveReadItems")
	private Set<ApplicationUser> haveReadUsers = new HashSet<>();

	@ManyToMany(mappedBy = "toReadItems")
	private Set<ApplicationUser> toReadUsers = new HashSet<>();

}
