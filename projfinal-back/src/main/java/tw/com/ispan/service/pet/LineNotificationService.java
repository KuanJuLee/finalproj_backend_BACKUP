package tw.com.ispan.service.pet;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

//此為發送消息功能
@Service
public class LineNotificationService {
	
	//lineMessagingClient專門用於通過 LINE Messaging API 與 LINE 平台進行通信，可藉此發訊息給用戶
	//通過構造方法@RequiredArgsConstructor注入  失敗!! 改手動寫建構子
    @Autowired
	private final LineMessagingClient lineMessagingClient;
    
    public LineNotificationService(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    public void sendNotification(String userId, String message) {
        TextMessage textMessage = new TextMessage(message);
        PushMessage pushMessage = new PushMessage(userId, textMessage);

        CompletableFuture<Void> future = lineMessagingClient.pushMessage(pushMessage)
                .thenAccept(response -> System.out.println("Message sent successfully"))
                .exceptionally(throwable -> {
                    System.err.println("Failed to send message: " + throwable.getMessage());
                    return null;
                });

        // 等待消息發送完成（選擇性）
        future.join();
    }
}

//@RequiredArgsConstructor 是來自Lombok函式庫的一個註解，用於自動生成包含所有final字段和被標記為 @NonNull 的字段的構造方法。(只有 final 字段或 @NonNull 字段會被包含在生成的構造方法中)