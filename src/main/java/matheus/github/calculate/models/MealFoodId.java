package matheus.github.calculate.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MealFoodId implements Serializable {

	@EqualsAndHashCode.Include
	@Column(name = "meal_id")
	private long mealId;

	@EqualsAndHashCode.Include
	@Column(name = "food_id")
	private long foodId;

}
