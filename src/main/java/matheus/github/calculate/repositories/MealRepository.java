package matheus.github.calculate.repositories;

import matheus.github.calculate.models.Meal;
import matheus.github.calculate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
	List<Meal> findAllByUser(User user);

	Optional<Meal> findByUserAndId(User user, Long id);
	boolean existsByUserAndId(User user, Long id);
}
