package matheus.github.calculate.repositories;

import matheus.github.calculate.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest(properties = {
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.datasource.url=jdbc:mysql://localhost:3306/calculate_macro_db_test"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MealFoodRepositoryTest {

	@Autowired
	private MealFoodRepository mealFoodRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private User user1;
	private Meal meal1;
	private Meal meal2;
	private Meal meal3;
	private Food food1;
	private Food food2;
	private Food food3;

	@BeforeEach
	void setUp() {
		user1 = new User();
		user1.setName("John Doe");
		user1.setUsername("John Doe Username");
		user1.setEmail("john.doe@gmail.com");
		user1.setPassword("johndoepassword");
		testEntityManager.persist(user1);

		meal1 = new Meal();
		meal1.setName("Breakfast");
		meal1.setUser(user1);

		meal2 = new Meal();
		meal2.setName("Lunch");
		meal2.setUser(user1);

		meal3 = new Meal();
		meal3.setName("Dinner");
		meal3.setUser(user1);

		food1 = new Food();
		food1.setName("Egg");
		food1.setCalories(155);
		food1.setProteins(13);
		food1.setCarbohydrates(1.1);
		food1.setFats(11);
		food1.setUser(user1);

		food2 = new Food();
		food2.setName("Bread");
		food2.setCalories(265);
		food2.setProteins(9);
		food2.setCarbohydrates(49);
		food2.setFats(3.2);
		food2.setUser(user1);

		food3 = new Food();
		food3.setName("Apple");
		food3.setCalories(52);
		food3.setProteins(0.3);
		food3.setCarbohydrates(14);
		food3.setFats(0.2);
		food3.setUser(user1);
	}

	@Test
	void findById_WithExistingId_ReturnMealFood() {
		Meal meal1 = testEntityManager.persist(this.meal1);
		Food food1 = testEntityManager.persist(this.food1);

		MealFoodId mealFoodId = new MealFoodId(meal1.getId(), food1.getId());
		MealFood mealFood = new MealFood(mealFoodId, meal1, food1, 10);

		MealFood mealFoodSaved = testEntityManager.persist(mealFood);

		MealFood mealFoodReceived = mealFoodRepository.findById(mealFoodId);

		assertThat(mealFoodReceived).isNotNull();
		assertThat(mealFoodReceived.getId()).isEqualTo(mealFoodSaved.getId());
		assertThat(mealFoodReceived.getFoodAmount()).isEqualTo(mealFoodSaved.getFoodAmount());
	}

	@Test
	void existsByUserAndMealAndFood_WithValidData_ReturnTrue() {
		Meal meal1 = testEntityManager.persist(this.meal1);
		Food food1 = testEntityManager.persist(this.food1);
		Food food2 = testEntityManager.persist(this.food2);

		MealFoodId mealFoodId = new MealFoodId(meal1.getId(), food1.getId());
		MealFood mealFood = new MealFood(mealFoodId, meal1, food1, 10);

		testEntityManager.persist(mealFood);

		boolean exists = mealFoodRepository.existsByUserAndMealAndFood(user1, meal1, food1);
		boolean exists2 = mealFoodRepository.existsByUserAndMealAndFood(user1, meal1, food2);
		assertThat(exists).isTrue();
		assertThat(exists2).isFalse();
	}

	@Test
	void findAllByUser_ReturnsAllMealFood() {
		Meal meal1 = testEntityManager.persist(this.meal1);
		Food food1 = testEntityManager.persist(this.food1);

		Food food2 = testEntityManager.persist(this.food2);

		MealFoodId mealFoodId = new MealFoodId(meal1.getId(), food1.getId());
		MealFood mealFood = new MealFood(mealFoodId, meal1, food1, 10);

		MealFoodId mealFoodId2 = new MealFoodId(meal1.getId(), food2.getId());
		MealFood mealFood2 = new MealFood(mealFoodId2, meal1, food2, 10);

		testEntityManager.persist(mealFood);
		testEntityManager.persist(mealFood2);

		List<MealFood> mealFoodList = mealFoodRepository.findAllByUser(user1);
		assertThat(mealFoodList).hasSize(2);
		assertThat(mealFoodList).hasOnlyElementsOfType(MealFood.class);
		assertThat(mealFoodList).hasSameElementsAs(List.of(mealFood, mealFood2));
	}


}