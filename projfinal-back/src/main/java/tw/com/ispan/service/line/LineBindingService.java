package tw.com.ispan.service.line;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.repository.admin.MemberRepository;

public class LineBindingService {
	
	@Autowired
	private final LineMessagingClient lineMessagingClient;
	@Autowired
	private MemberRepository memberRepository;
	
	public LineBindingService(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}
	
	
	// 如果用戶希望綁定，透過此方法產生唯一綁定token鏈結，發送回去給用戶點選發送request，將自己lineid和token傳進controller
		public String generateBindingLink(Integer memberId) {
			// 使用 UUID 生成唯一 Token
			String bindingToken = UUID.randomUUID().toString();

			// 保存到資料庫（綁定 token 和會員 ID 的映射）
			Optional<Member> memberOpt = memberRepository.findById(memberId);
			if (memberOpt.isPresent()) {
				Member member = memberOpt.get();

				// 設置 Token 和過期時間
				member.setBindingToken(bindingToken);
				member.setBindingTokenExpiry(LocalDateTime.now().plusMinutes(10)); // Token 過期時間為10分鐘

				// 保存到數據庫
				memberRepository.save(member);
			} else {
				throw new IllegalArgumentException("會員不存在");
			}

			// 返回綁定鏈接
			return "http://localhost:8080/line/bind?token=" + bindingToken;   //典型的 RESTful URL
		}
		
		
		//傳送用戶綁定鏈結的方法
		public void sendBindingMessage(String userId, String bindingLink) {
	        // 創建消息內容
	        TextMessage textMessage = new TextMessage("點擊此連結以完成綁定: " + bindingLink);

	        // 發送消息
	        PushMessage pushMessage = new PushMessage(userId, textMessage);
	        lineMessagingClient.pushMessage(pushMessage)
	            .thenAccept(response -> System.out.println("綁定消息已成功發送"))
	            .exceptionally(throwable -> {
	                System.err.println("綁定消息發送失敗: " + throwable.getMessage());
	                return null;
	            });
	    }
}
