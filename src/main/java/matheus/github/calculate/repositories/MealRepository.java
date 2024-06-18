package matheus.github.calculate.repositories;

import matheus.github.calculate.models.Meal;
import matheus.github.calculate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
	List<Meal> findAllByUser(User user);

	Optional<Meal> findByUserAndId(User user, Long id);
	boolean existsByUserAndId(User user, Long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM Meal m WHERE m.user = :user AND m.id = :mealid")
	void deleteByUserAndMealId(@Param("user") User user, @Param("mealid") Long mealid);

}
