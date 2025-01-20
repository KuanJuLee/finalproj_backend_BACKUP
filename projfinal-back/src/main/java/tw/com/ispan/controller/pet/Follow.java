package tw.com.ispan.controller.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.jwt.JsonWebTokenUtility;
import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.repository.pet.FollowRepository;
import tw.com.ispan.repository.pet.RescueCaseRepository;

//此為會員追蹤某案件
@RestController
@RequestMapping(path = { "/Case/follow" })
public class Follow {

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RescueCaseRepository rescueCaseRepository;
	
	@Autowired
	JsonWebTokenUtility jsonWebTokenUtility;

	// 增加追蹤案件
	@PutMapping("/add")
    public ResponseEntity<String> followCase(@RequestHeader("Authorization") String token,  @RequestAttribute("memberId") Integer memberId, @RequestParam Integer rescueCaseId) {
		
		System.out.println(memberId);
		
		
		return null;
	}
}
