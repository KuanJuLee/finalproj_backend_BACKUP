package tw.com.ispan.service.line;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

	
	// 拿line callback返回的code(短暫效期)，去交換accesstoken(用於表示有權利獲取line中用戶訊息)
	public String exchangeCodeForAccessToken(String code) {
		//??
		RestTemplate restTemplate = new RestTemplate();

		// 構造request body
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("grant_type", "authorization_code");
		requestBody.add("code", code);
		requestBody.add("redirect_uri", redirectUri);
		requestBody.add("client_id", channelId);
		requestBody.add("client_secret", channelSecret);

		// 發送請求
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, new HttpHeaders());
		ResponseEntity<Map> response = restTemplate.postForEntity("https://api.line.me/oauth2/v2.1/token", request,
				Map.class);

		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			return (String) response.getBody().get("access_token");
		}

		return null;
	}
	
//	使用 Access Token 調用 LINE 的 /v2/profile API
//	public LineProfile getUserProfile(String accessToken) {
//	    RestTemplate restTemplate = new RestTemplate();
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.set("Authorization", "Bearer " + accessToken);
//
//	    HttpEntity<String> request = new HttpEntity<>(headers);
//	    ResponseEntity<LineProfile> response = restTemplate.exchange(
//	            "https://api.line.me/v2/profile",
//	            HttpMethod.GET,
//	            request,
//	            LineProfile.class
//	    );
//
//	    return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
	}
