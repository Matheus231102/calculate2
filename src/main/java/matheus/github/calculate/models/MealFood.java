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

	@EmbeddedId
	private MealFoodId id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, insertable = false, updatable = false)
	private Meal meal;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, insertable = false, updatable = false)
	private Food food;

	@Column(nullable = false)
	private Integer foodAmount;

}

