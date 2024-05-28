package matheus.github.calculate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_meals_foods")
public class MealFood {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "meal_id", nullable = false)
	private Meal meal;

	@ManyToOne
	@JoinColumn(name = "food_id", nullable = false)
	private Food food;

	@Column(nullable = false)
	private Integer foodAmount;

}
