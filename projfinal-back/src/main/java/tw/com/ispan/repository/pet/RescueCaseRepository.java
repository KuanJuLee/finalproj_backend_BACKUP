package tw.com.ispan.repository.pet;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tw.com.ispan.domain.pet.RescueCase;

public interface RescueCaseRepository extends JpaRepository<RescueCase, Integer>, JpaSpecificationExecutor<RescueCase> {

	@Query("SELECT r FROM RescueCase r")
	List<RescueCase> findAllCases(Pageable pageable);
}
