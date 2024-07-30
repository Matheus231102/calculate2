package matheus.github.calculate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * Objeto que representa a entidade (Food) no sistema, valores não nulos e com valor mínimo 0.0 (name, calories, proteins, carbohydrates, fats)
 * atributo price não é obrigatório
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@DecimalMin(value = "0.0")
	private double calories;

	@NotNull
	@DecimalMin(value = "0.0")
	private double proteins;

	@NotNull
	@DecimalMin(value = "0.0")
	private double carbohydrates;

	@NotNull
	@DecimalMin(value = "0.0")
	private double fats;

	private BigDecimal price;

}
