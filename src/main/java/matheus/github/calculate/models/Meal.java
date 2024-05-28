package matheus.github.calculate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_meals")
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = false)
	private String name;

	@OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
	private List<MealFood> mealFoods;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

}
