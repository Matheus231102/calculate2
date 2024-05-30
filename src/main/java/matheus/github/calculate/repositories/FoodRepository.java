package matheus.github.calculate.repositories;

import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

	List<Food> findAllByUser(User user);
	Optional<Food> findByUserAndId(User user, Long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM Food f WHERE f.user = :user")
	void deleteAllByUser(User user);

	@Transactional
	@Modifying
	@Query("DELETE FROM Food f WHERE f.id = :id and f.user = :user")
	void deleteByUserAndId(User user, Long id);

	boolean existsByUserAndId(User user, Long id);

}
