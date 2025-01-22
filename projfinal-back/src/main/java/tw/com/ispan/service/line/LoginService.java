package tw.com.ispan.service.line;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {

	@Value("${line.login.channel-id}")
	private String channelId;

	@Value("${line.login.channel-secret}")
	private String channelSecret;

	@Value("${line.login.redirect-uri}")
	private String redirectUri;

	@Autowired
	private StateRedisService stateRedisService;

	// 產生授權url中的state(用於驗證)
	public String generateState() {
		// 生成唯一的 state
		String state = UUID.randomUUID().toString();

		// 儲存到 Redis 並設置過期時間(10分鐘)
		stateRedisService.saveState(state);

		return state;
	}

	// 產生授權url，返回給前端
	public String generateAuthorizeUrl(String state) {
		return "https://access.line.me/oauth2/v2.1/authorize" + "?response_type=code" + "&client_id=" + channelId
				+ "&redirect_uri=" + redirectUri + "&scope=profile%20openid" + "&state=" + state;
	}
}
