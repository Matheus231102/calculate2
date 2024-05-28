package matheus.github.calculate.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "tb_foods")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Float calories;
	private Float proteins;
	private Float carbohydrates;
	private Float fats;

	@ManyToOne
	private User user;

}
