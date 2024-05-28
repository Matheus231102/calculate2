package matheus.github.calculate.repositories;

import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
	List<Food> findAllByUser(User user);
}
