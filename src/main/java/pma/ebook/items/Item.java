package pma.ebook.items;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import pma.ebook.users.ApplicationUser;

@Entity
@Getter
@Setter
public class Item {

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
