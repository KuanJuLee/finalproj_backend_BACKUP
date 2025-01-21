package tw.com.ispan.controller.pet;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.service.line.LineBindingService;
import tw.com.ispan.service.line.LineNotificationService;

//用來接收line平台中因和linebot互動產生的Webhook回調事件，LINE 平台會以 POST 的方式將事件推送到你的 Webhook URL
@RestController
@RequestMapping("/webhook")
public class LineWebhookController {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private LineBindingService lineBindingService;
	@Autowired
	LineNotificationService lineNotificationService;
	
	//接收並處理來自 LINE Messaging API Webhook 的回調事件
	//step1 用戶點選機器人按鈕後並自動追蹤機器人帳號後，馬上發送同時插入LineTemporaryBinding表產生token並和LINEId綁定
	@PostMapping
	public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> request) {
		
		System.out.println("hihihi");
		//@RequestBody 從HTTP請求中接收JSON資料，並自動將其轉換為Java的Map對象
		//events 是一個包含多個事件的陣列，將提取到的 events 強制轉換為 List，每個事件用 Map<String, Object> 表示
		List<Map<String, Object>> events = (List<Map<String, Object>>) request.get("events");
		
		for (Map<String, Object> event : events) {
			
			//從事件的 Map 中提取 type 字段，這個字段表示事件的類型，其中事件follow：用戶加為好友或解除封鎖。 message：用戶發送消息。unfollow：用戶取消好友。join：Bot 被邀請加入群組或聊天室。
			String type = (String) event.get("type");

			if ("follow".equals(type)) {
				// 從事件中提取 source 字段，這是關於事件來源的數據，再提取用戶的 LINE ID
				Map<String, Object> source = (Map<String, Object>) event.get("source");
				String lineId = (String) source.get("userId");
				
				//在此獲得lineId後，透過產生token插入臨時表中，綁定lineId和token。並產生要發送給用戶line訊息的綁定鏈結
	            String bindingToken = lineBindingService.generateBindingLinkForLineId(lineId);
	            String bindingLink = "https://ebbe-36-225-218-14.ngrok-free.app/line/bindComplete?token=" + bindingToken ;
	            

	            // 發送綁定鏈接到用戶的 LINE
	            lineNotificationService.sendBindingMessage(lineId, bindingLink);

	            System.out.println("新用戶的 LINE ID: " + lineId + " 已收到綁定消息。");
				
			}
		}
		
		//回傳HTTP response，LINE平台需要接收到HTTP 200回應已確定Webhook請求處理成功。如果LINE平台未收到成功回應，將視為事件處理失敗，並可能重試推送
		return ResponseEntity.ok("Webhook received");
	}
}
