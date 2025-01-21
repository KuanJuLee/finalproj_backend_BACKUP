package tw.com.ispan.service.line;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.repository.admin.MemberRepository;

//此為發送消息功能
@Service
public class LineNotificationService {

	// lineMessagingClient專門用於通過 LINE Messaging API 與 LINE 平台進行通信，可藉此發訊息給用戶
	// 通過構造方法@RequiredArgsConstructor注入 失敗!! 改手動寫建構子
	@Autowired
	private final LineMessagingClient lineMessagingClient;
	@Autowired
	private MemberRepository memberRepository;

	public LineNotificationService(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}

	// 此方法用來傳送通知
	public void sendNotification(String userId, String message) {
		TextMessage textMessage = new TextMessage(message);
		PushMessage pushMessage = new PushMessage(userId, textMessage);

		CompletableFuture<Void> future = lineMessagingClient.pushMessage(pushMessage)
				.thenAccept(response -> System.out.println("Message sent successfully")).exceptionally(throwable -> {
					System.err.println("Failed to send message: " + throwable.getMessage());
					return null;
				});

		// 等待消息發送完成（選擇性）
		future.join();
	}

	
}

