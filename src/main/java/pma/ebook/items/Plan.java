package pma.ebook.items;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import pma.ebook.users.ApplicationUser;

@Entity
@Getter
@Setter
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(
		mappedBy = "plan",
		cascade = CascadeType.ALL
	)
	private List<ApplicationUser> users = new ArrayList<>();

	@OneToMany(
		mappedBy = "plan",
		cascade = CascadeType.ALL
	)
	private List<ItemCollection> collections = new ArrayList<>();

}
