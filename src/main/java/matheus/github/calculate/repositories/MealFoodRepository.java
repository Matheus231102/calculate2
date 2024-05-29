package matheus.github.calculate.repositories;

import matheus.github.calculate.models.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {

}
