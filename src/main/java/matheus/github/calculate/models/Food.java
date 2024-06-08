package matheus.github.calculate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_foods")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private double calories;

	@Column(nullable = false)
	private double proteins;

	@Column(nullable = false)
	private double carbohydrates;

	@Column(nullable = false)
	private double fats;

	@Column(nullable = false)
	private BigDecimal price;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
	private List<MealFood> mealFoods;

}
