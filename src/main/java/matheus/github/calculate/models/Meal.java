package matheus.github.calculate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(nullable = false)
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
	private List<MealFood> mealFoods;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@PrePersist
	private void onCreate() {
		setMealFoods(List.of());
	}

}
