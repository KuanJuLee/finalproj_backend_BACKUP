package tw.com.ispan.repository.pet;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.ispan.domain.pet.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
	
	//Spring Data JPA 提供的一種簡化資料庫查詢的方式，基於方法名稱自動生成對應的查詢語句
//	boolean existsByRescueCaseIdAndMemberId(Integer rescueCaseId, Integer memberId);
//    boolean existsByLostCaseIdAndMemberId(Integer lostCaseId, Integer memberId);
//    boolean existsByAdoptionCaseIdAndMemberId(Integer adoptionCaseId, Integer memberId);
//    
    
}
