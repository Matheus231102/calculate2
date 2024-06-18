package matheus.github.calculate.repositories;

import matheus.github.calculate.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {

	@Query("SELECT CASE WHEN (COUNT(mf)> 0) THEN TRUE ELSE FALSE END FROM MealFood mf WHERE mf.food.user = :user AND mf.meal = :meal AND mf.food = :food")
	boolean existsByUserAndMealAndFood(@Param("user") User user, @Param("meal") Meal meal, @Param("food") Food food);

	@Query("SELECT mf FROM MealFood mf INNER JOIN mf.food f INNER JOIN mf.meal.user")
	List<MealFood> findAllByUser(@Param("user") User user);

	// Usada no caso em que o Food é removido e o MealFood associados precisam ser removidos em cascata
	@Transactional
	@Modifying
	@Query("DELETE FROM MealFood mf WHERE mf.food.id = :foodid AND mf.food.user = :user")
	void deleteAllByUserAndFoodId(@Param("user") User user, @Param("foodid") long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM MealFood mf WHERE mf.meal.id = :mealid AND mf.food.user = :user")
	void deleteAllByUserAndMealId(@Param("user") User user, @Param("mealid") long mealid);

	@Transactional
	@Modifying
	@Query("DELETE FROM MealFood mf WHERE mf.food.user = :user AND mf.meal.id = :mealid AND mf.food.id = :foodid")
	void deleteAllByUserAndMealIdAndFoodId(@Param("user") User user, @Param("mealid") long mealId, @Param("foodid") long foodId);

	MealFood findById(MealFoodId mealFoodId);


}
