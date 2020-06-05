package pma.ebook.items;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemCollection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(
		mappedBy = "collection",
		cascade = CascadeType.ALL
	)
	private List<ItemEntity> items = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Plan plan;
}
