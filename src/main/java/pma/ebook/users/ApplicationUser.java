package pma.ebook.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pma.ebook.items.ItemEntity;
import pma.ebook.items.Plan;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;

	private String username;

	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	private Plan plan;

	@ManyToMany(cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	})
	@JoinTable(name = "favorites",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id")
	)
	private final Set<ItemEntity> favoriteItems = new HashSet<>();

	@ManyToMany(cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	})
	@JoinTable(name = "have_read",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id")
	)
	private final Set<ItemEntity> haveReadItems = new HashSet<>();

	@ManyToMany(cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	})
	@JoinTable(name = "to_read",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id")
	)
	private final Set<ItemEntity> toReadItems = new HashSet<>();
}
