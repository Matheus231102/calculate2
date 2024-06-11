package matheus.github.calculate.repositories;

import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {

	@Query("""
			SELECT mf FROM MealFood mf 
			INNER JOIN mf.food f 
			INNER JOIN mf.meal.user 
			""")
	List<MealFood> findAllByUser(@Param("user") User user);

	@Transactional
	@Modifying
	@Query("DELETE FROM MealFood mf WHERE mf.food.id = :id AND mf.food.user.username = :username")
	void deleteAllByUserAndFoodId(@Param("username") String username, @Param("id") long id);
}
