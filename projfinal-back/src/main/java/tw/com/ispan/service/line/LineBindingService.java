package tw.com.ispan.service.line;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.domain.pet.LineTemporaryBinding;
import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.repository.pet.LineTemporaryBindingRepository;

@Service
@Transactional
public class LineBindingService {

	//怎麼拿到這個用戶產生的token??
	
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private LineTemporaryBindingRepository lineTemporaryBindingRepository;
	
	//產生用戶lineId的綁定連結
	public String generateBindingLinkForLineId(String lineId) {
	    // 使用 UUID 生成唯一 Token
	    String bindingToken = UUID.randomUUID().toString();

	    // 將 token 和 LINE ID 暫存至數據庫
	    LineTemporaryBinding lineTemporaryBinding = new LineTemporaryBinding();
	    lineTemporaryBinding.setLineId(lineId);
	    lineTemporaryBinding.setBindingToken(bindingToken);
	    lineTemporaryBinding.setExpiryTime(LocalDateTime.now().plusMinutes(10));
	    lineTemporaryBindingRepository.save(lineTemporaryBinding);

	    return bindingToken;
	}
	
	
	
	
	
	// 當用戶發起綁定請求，透過此方法產生唯一綁定token鏈結，發送回去給用戶點選發送request，將自己lineid和token傳進controller
//	public String generateBindingLink(Integer memberId) {
//		// 使用 UUID 生成唯一 Token
//		String bindingToken = UUID.randomUUID().toString();
//
//		// 保存到資料庫（綁定 token 和會員 ID 的映射）
//		Optional<Member> memberOpt = memberRepository.findById(memberId);
//		if (memberOpt.isPresent()) {
//			Member member = memberOpt.get();
//
//			// 設置 Token 和過期時間
//			member.setBindingToken(bindingToken);
//			member.setBindingTokenExpiry(LocalDateTime.now().plusMinutes(10)); // Token 過期時間為10分鐘
//
//			// 保存到數據庫
//			memberRepository.save(member);
//		} else {
//			throw new IllegalArgumentException("會員不存在");
//		}
//
//		// 返回綁定鏈接
//		return "https://586f-1-160-6-252.ngrok-free.app/line/bind?token=" + bindingToken; // 典型RESTful URL
//	}

	// 完成綁定// 查找對應的會員並驗證token
	public ResponseEntity<String> completeBinding(String token, String lineUserId) {
		
		// 查找對應的會員並驗證token
		Optional<Member> memberOpt = memberRepository.findByBindingToken(token);
		if (memberOpt.isEmpty()) {
			return ResponseEntity.badRequest().body("無效的綁定請求！"); // 此Token不存在
		}

		Member member = memberOpt.get();

		// 驗證 Token 是否過期（可選）
		if (member.getBindingTokenExpiry().isBefore(LocalDateTime.now())) {
			return ResponseEntity.badRequest().body("綁定已過期！請於10分鐘內進行操作");
		}

		// 綁定 LINE User ID
		member.setUserLineId(lineUserId);
		member.setBindingToken(null); // 清除 Token，防止重複綁定
		member.setBindingTokenExpiry(null);
		memberRepository.save(member);

		return ResponseEntity.ok("綁定成功！");
	}
}
