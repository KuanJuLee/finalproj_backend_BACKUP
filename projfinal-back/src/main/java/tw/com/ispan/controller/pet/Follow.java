package tw.com.ispan.controller.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.dto.pet.RescueCaseResponse;
import tw.com.ispan.jwt.JsonWebTokenUtility;
import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.repository.pet.FollowRepository;
import tw.com.ispan.repository.pet.RescueCaseRepository;
import tw.com.ispan.service.pet.RescueCaseService;

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
	
	@Autowired
	private RescueCaseService rescueCaseService;


	// 增加追蹤案件
	@PutMapping("/add")
    public RescueCaseResponse followCase(@RequestHeader("Authorization") String token,  @RequestAttribute("memberId") Integer memberId, @RequestParam Integer caseId) {
		
		
		RescueCaseResponse response = new RescueCaseResponse();
		
		//1. 接收到會員Id並驗證此會員存在
		System.out.println(memberId);
		
		
		//2. 驗證此案件存在
		if (caseId == null) {
			response.setSuccess(false);
			response.setMessage("必須給予案件id");
			return response;
		} else if (!rescueCaseService.exists(caseId)) {
			response.setSuccess(false);
			response.setMessage("此案件id不存在於資料中");
			return response;
		}
		
		//3. 增添follow表資料
		
		
		return null;
	}
}
