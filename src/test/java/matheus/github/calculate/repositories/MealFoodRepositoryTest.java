package matheus.github.calculate.repositories;

import matheus.github.calculate.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


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

	@BeforeEach
	void setUp() {
		User user1 = new User();
		user1.setName("John Doe");
		testEntityManager.persist(user1);

		Meal meal1 = new Meal();
		meal1.setName("Breakfast");
		meal1.setUser(user1);
		testEntityManager.persist(meal1);

		Meal meal2 = new Meal();
		meal2.setName("Lunch");
		meal2.setUser(user1);
		testEntityManager.persist(meal2);

		Meal meal3 = new Meal();
		meal3.setName("Dinner");
		meal3.setUser(user1);
		testEntityManager.persist(meal3);

		Food food1 = new Food();
		food1.setName("Egg");
		food1.setCalories(155);
		food1.setProteins(13);
		food1.setCarbohydrates(1.1);
		food1.setFats(11);
		food1.setUser(user1);
		testEntityManager.persist(food1);

		Food food2 = new Food();
		food2.setName("Bread");
		food2.setCalories(265);
		food2.setProteins(9);
		food2.setCarbohydrates(49);
		food2.setFats(3.2);
		food2.setUser(user1);
		testEntityManager.persist(food2);

		Food food3 = new Food();
		food3.setName("Apple");
		food3.setCalories(52);
		food3.setProteins(0.3);
		food3.setCarbohydrates(14);
		food3.setFats(0.2);
		food3.setUser(user1);
		testEntityManager.persist(food3);


		MealFoodId mealFoodId1 = new MealFoodId(meal1.getId(), food1.getId());
		MealFood mealFood1 = new MealFood(mealFoodId1, meal1, food1, 2);
		testEntityManager.persist(mealFood1);

		MealFoodId mealFoodId2 = new MealFoodId(meal2.getId(), food2.getId());
		MealFood mealFood2 = new MealFood(mealFoodId2, meal2, food2, 3);
		testEntityManager.persist(mealFood2);

		MealFoodId mealFoodId3 = new MealFoodId(meal3.getId(), food3.getId());
		MealFood mealFood3 = new MealFood(mealFoodId3, meal3, food3, 1);
		testEntityManager.persist(mealFood3);
	}


}