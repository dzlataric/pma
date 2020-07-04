package pma.ebook.bookstore;

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
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserSettingsEntity settings;

	public void addFavorite(final ItemEntity favorite) {
		favoriteItems.add(favorite);
		favorite.getFavoriteUsers().add(this);
	}

	public void removeFavorite(final ItemEntity favorite) {
		favoriteItems.remove(favorite);
		favorite.getFavoriteUsers().remove(this);
	}

	public void addToRead(final ItemEntity toRead) {
		toReadItems.add(toRead);
		toRead.getToReadUsers().add(this);
	}

	public void removeToRead(final ItemEntity toRead) {
		toReadItems.remove(toRead);
		toRead.getToReadUsers().remove(this);
	}

	public void addHaveRead(final ItemEntity haveRead) {
		haveReadItems.add(haveRead);
		haveRead.getHaveReadUsers().add(this);
	}

	public void removeHaveRead(final ItemEntity haveRead) {
		haveReadItems.remove(haveRead);
		haveRead.getHaveReadUsers().remove(this);
	}

}
