package tw.com.ispan.controller.pet;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.repository.admin.MemberRepository;

@RestController
@RequestMapping("/line")
public class LineBindingController {
	
    @Autowired
    private MemberRepository memberRepository;
    
    
    //用戶發起綁定請求(點擊某個綁定按鈕或掃描QR Code)，程式返回綁定鏈結給用戶
    
    
    
    //用戶點擊綁定鏈接後，系統需要處理該請求，完成 LINE ID 與會員的綁定
    @PostMapping("/bind")
    public ResponseEntity<String> bindLineAccount(@RequestParam String token, @RequestBody String lineUserId) {
    	 
    	// 查找對應的會員並驗證token
        Optional<Member> memberOpt = memberRepository.findByBindingToken(token);
        if (memberOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("無效的綁定請求！");  //此Token不存在
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
