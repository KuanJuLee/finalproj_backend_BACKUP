package tw.com.ispan.repository.pet.forRescue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.ispan.domain.pet.forRescue.RescueProgress;

public interface RescueProgressRepository extends JpaRepository<RescueProgress, Integer>{
	
	 // 根據 RescueCaseId 查詢該案件救援進度
	@Query("SELECT rp FROM RescueProgress rp JOIN RescueCase rc ON rc.rescueCaseId = :caseId")
	List<RescueProgress> findByRescueCaseId(@Param("caseId") Integer caseId);
}
