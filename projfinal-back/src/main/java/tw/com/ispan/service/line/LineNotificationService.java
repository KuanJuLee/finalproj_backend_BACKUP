package tw.com.ispan.service.line;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.container.FlexContainer;

import tw.com.ispan.repository.admin.MemberRepository;

//此為發送消息功能
@Service
@Transactional
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

	 // 發送綁定消息的方法
    public void sendBindingMessage(String userId, Integer memberId) {
        try {
            // 動態生成 Flex Message 的 JSON
            String flexMessageJson = "{"
                    + "  \"type\": \"flex\","
                    + "  \"altText\": \"綁定您的帳號\","
                    + "  \"contents\": {"
                    + "    \"type\": \"bubble\","
                    + "    \"body\": {"
                    + "      \"type\": \"box\","
                    + "      \"layout\": \"vertical\","
                    + "      \"contents\": ["
                    + "        {"
                    + "          \"type\": \"text\","
                    + "          \"text\": \"綁定您的帳號\","
                    + "          \"weight\": \"bold\","
                    + "          \"size\": \"lg\""
                    + "        },"
                    + "        {"
                    + "          \"type\": \"button\","
                    + "          \"action\": {"
                    + "            \"type\": \"postback\","
                    + "            \"label\": \"綁定\","
                    + "            \"data\": \"action=bind&memberId=" + memberId + "\""
                    + "          },"
                    + "          \"style\": \"primary\""
                    + "        }"
                    + "      ]"
                    + "    }"
                    + "  }"
                    + "}";

            // 使用 ObjectMapper 將 JSON 字串轉換為 FlexContainer 對象
            ObjectMapper objectMapper = new ObjectMapper();
            FlexContainer flexContainer = objectMapper.readValue(flexMessageJson, FlexContainer.class);

            // 創建 Flex Message 對象
            FlexMessage flexMessage = new FlexMessage("綁定您的帳號", flexContainer);

            // 發送 Flex Message 給用戶
            lineMessagingClient.pushMessage(new PushMessage(userId, flexMessage))
                    .thenAccept(response -> System.out.println("綁定消息已成功發送"))
                    .exceptionally(throwable -> {
                        System.err.println("綁定消息發送失敗: " + throwable.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
	
	
	// 此方法用來傳送協尋通知
	public void sendNotification(String userLineId, String message) {
		TextMessage textMessage = new TextMessage(message);
		PushMessage pushMessage = new PushMessage(userLineId, textMessage);

		CompletableFuture<Void> future = lineMessagingClient.pushMessage(pushMessage)
				.thenAccept(response -> System.out.println("Message sent successfully")).exceptionally(throwable -> {
					System.err.println("Failed to send message: " + throwable.getMessage());
					return null;
				});

		// 等待消息發送完成（選擇性）
		future.join();
	}
	
	
	// 傳送用戶綁定鏈結的方法
	public void sendBindingMessage(String LineId, String bindingLink) {

		// 創建消息內容
		TextMessage textMessage = new TextMessage("點擊此連結以完成綁定: " + bindingLink);

		// 發送消息
		PushMessage pushMessage = new PushMessage(LineId, textMessage);
		lineMessagingClient.pushMessage(pushMessage).thenAccept(response -> System.out.println("綁定消息已成功發送"))
				.exceptionally(throwable -> {
					System.err.println("綁定消息發送失敗: " + throwable.getMessage());
					return null;
				});
	}

	
}

