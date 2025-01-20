package tw.com.ispan.service.pet;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.pet.AdoptionCase;
import tw.com.ispan.domain.pet.Follow;
import tw.com.ispan.domain.pet.LostCase;
import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.repository.pet.AdoptionCaseRepository;
import tw.com.ispan.repository.pet.FollowRepository;
import tw.com.ispan.repository.pet.LostCaseRepository;
import tw.com.ispan.repository.pet.RescueCaseRepository;

@Service
@Transactional
public class CaseService {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private RescueCaseRepository rescueCaseRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private LostCaseRepository lostCaseRepository;
	@Autowired
	private AdoptionCaseRepository adoptionCaseRepository;

	// 新增追蹤
	public Follow addFollow(Integer memberId, Integer caseId, String caseType) {
		Follow follow = new Follow();

		// 當按下follow要能區分使用者是對rescue, lost還是adoption case，因為相同caseId在這三表中都有，要查對表

		switch (caseType.toLowerCase()) {
		case "rescue":
			RescueCase rescueCase = rescueCaseRepository.findById(caseId)
					.orElseThrow(() -> new IllegalArgumentException("案件ID" + caseId + "的RescueCase 不存在"));
			follow.setRescueCase(rescueCase);
			break;

		case "lost":
			LostCase lostCase = lostCaseRepository.findById(caseId)
					.orElseThrow(() -> new IllegalArgumentException("案件ID" + caseId + "LostCase 不存在"));
			follow.setLostCase(lostCase);
			break;

		case "adoption":
			AdoptionCase adoptionCase = adoptionCaseRepository.findById(caseId)
					.orElseThrow(() -> new IllegalArgumentException("案件ID" + caseId + "AdoptionCase 不存在"));
			follow.setAdoptionCase(adoptionCase);
			break;

		default:
			throw new IllegalArgumentException("未知的案件類型：" + caseType);
		}

		// 設置會員
		follow.setMember(memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("會員不存在")));
		
		//設置時間
		follow.setFollowDate(LocalDateTime.now()); 
		
		Follow saveFollow = followRepository.save(follow);   //返回物件會被加進followId流水號
		return saveFollow;
	}

	// 用於驗證某類型案件表中是否存在此案件id
	public boolean caseExists(Integer caseId, String caseType) {
		switch (caseType.toLowerCase()) {
		case "rescue":
			return rescueCaseRepository.existsById(caseId);
		case "lost":
			return lostCaseRepository.existsById(caseId);
		case "adoption":
			return adoptionCaseRepository.existsById(caseId);
		default:
			return false;
		}
	}

	
	// 確認follow表中是否已經存在某caseType和caseId加上某memberId的組合
	public boolean checkFollow(Integer caseId, String caseType, Integer memberId) {
		
		
		
		
		
		return true;
	}
	
}
