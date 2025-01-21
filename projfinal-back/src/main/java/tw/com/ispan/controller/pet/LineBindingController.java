package tw.com.ispan.controller.pet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import tw.com.ispan.domain.pet.LineTemporaryBinding;
import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.repository.pet.LineTemporaryBindingRepository;
import tw.com.ispan.service.line.LineBindingService;
import tw.com.ispan.service.line.LineNotificationService;
import tw.com.ispan.service.line.RedisService;

@RestController
@RequestMapping("/line")
public class LineBindingController {
	
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LineBindingService lineBindingService;
    @Autowired
    private LineNotificationService lineNotificationService;
    @Autowired
	private LineTemporaryBindingRepository lineTemporaryBindingRepository;
    @Autowired
    private RedisService redisService;
    
    //步驟: 用戶點選加入好友(linebot按鈕，在此超連結中夾帶memberId)後，在Webhook處理follow 事件controller中，可獲得用戶LineID，藉此產生綁定鏈結後
    //回傳訊息給用戶。點擊綁定鏈接，發送token url進到/bindComplete中)，在內透過於member表中尋找相同token藉此將lineid插入member表中
    
    //step1 此為用戶點選跳轉超連結後的中間層，用於紀錄夾帶參數memberId到redis暫存資料庫，存完重定向到 LINE 的官方連結
    @GetMapping
    public void redirectToLine(@RequestParam("memberId") String memberId, HttpServletResponse response) throws IOException {
        
    	// 提前保存 memberId，方式採用暫時存儲在Redis或資料庫。也可存在自己設置的暫存資料表
    	redisService.saveMemberId(memberId);

        // 重定向到 LINE 的 URL，仍然挾帶memberId(作用為何???)
        String lineRedirectUrl = "https://line.me/R/ti/p/@310pndih";
        response.sendRedirect(lineRedirectUrl);
        
     // 發送綁定確認消息給用戶(內含memberId)
        lineBindingService.sendBindingMessageToUser(memberId);
        
    }
    
    
    
    //把這個controller從在加入好友(linebot按鈕)中
    @GetMapping("/bindRequest")
    public ResponseEntity<String> bindRequest(@RequestHeader("Authorization") String token, @RequestAttribute("memberId") Integer memberId) {
        
    	// 1.驗證token(能通過JsonWebTokenInterceptor攔截器即驗證)，表示為會員的操作
    	System.out.println("此為會員id" + memberId + "執行line綁定請求");
    	
    	// 2. 伺服器生成綁定 Token ，此時會員表中此會員ID資料會綁定上綁定 Token
//    	String url = lineBindingService.generateBindingLink(memberId);
    	//將http://localhost:8080/line/bind?token=<bindingToken>
    	
    	
    	return ResponseEntity.ok("請稍後，完成綁定流程...");
    }
    
    
    //用戶點擊綁定鏈接後，系統需要處理該請求，完成 LINE ID 與會員的綁定
    @GetMapping("/bindComplete")
    public ResponseEntity<String> bindComplete(@RequestParam String bindingToken,  @RequestParam String authToken) {
    	 
    	// 從暫存表中查找 token
        Optional<LineTemporaryBinding> bindingOpt = lineTemporaryBindingRepository.findByBindingToken(bindingToken);
        if (bindingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("綁定token不存在於資料表中，請用戶重新加入好友");
        }

        LineTemporaryBinding binding = bindingOpt.get();

        // 驗證 token 是否過期
        if (binding.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("綁定token已過期！");
        }

        // 綁定 LINE ID 和 memberId
        String lineId = binding.getLineId();
        //到底怎麼拿到memberid!!!!! 
//        memberRepository.bindLineIdandMemberId(memberId, lineId);

        // 刪除暫存的綁定記錄
        lineTemporaryBindingRepository.delete(binding);

        return ResponseEntity.ok("綁定成功！");
    }	
    
    
    
    //https://line.me/R/ti/p/@310pndih 為linebot跳轉超連結，跳轉後自動追蹤(qrcode放置於專案data資料夾中)
    
}
