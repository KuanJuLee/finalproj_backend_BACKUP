package tw.com.ispan.controller.line;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import tw.com.ispan.service.line.LoginService;
import tw.com.ispan.service.line.StateRedisService;

@CrossOrigin(origins = "http://localhost:5173")  //允許前端不同的主機或埠運行下可訪問這個contorller
@RestController
@RequestMapping("/line")
public class LineLoginController {

	@Value("${line.login.channel-id}")
	private String clientId;

	@Value("${line.login.channel-secret}")
	private String clientSecret;

	@Value("${line.login.redirect-uri}")
	private String redirectUri;    
	

	@Autowired
	private StateRedisService stateRedisService;
	@Autowired
	private LoginService loginService;

	// 用於產生授權連結，回傳給前端
	@GetMapping("/authorize")
	public ResponseEntity<String> authorizeUser() {
		
		//line登入不用驗證是會員!!!記得要排除於驗證jwt路徑中
		
		String state = loginService.generateState(); // 生成並存儲 state
		String authorizeUrl = loginService.generateAuthorizeUrl(state); // 構造授權 URL
		return ResponseEntity.ok(authorizeUrl); // 返回給前端
	}

//	 此為用戶點擊授權連結後，line會發出一個callback request(自定義於平台中)，url中夾帶code和state
//	 https://yourdomain.com/callback?code=abcd1234&state=xyz789
//	 state是OAuth 2.0和OpenID Connect授權流程中的一個安全機制，旨在防止跨站請求偽造（CSRF）攻擊，並確保授權請求的完整性
	@GetMapping("/callback")
	public ResponseEntity<String> handleCallback(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws IOException {
		// 驗證 state(創造授權連結時主動加進去的亂數，驗證相同確保授權請求從用戶的瀏覽器發送過成未被篡改，也允許伺服器在回調中識別是哪個請求發起的授權流程)
		if (!stateRedisService.validateState(state)) {
			return ResponseEntity.badRequest().body("Invalid or expired state");
		}
		
		System.out.println(code + "和" + state);
		// 驗證成功，刪除 state
		stateRedisService.deleteState(state);

		// 處理授權碼邏輯，換取accessToken
		String accessToken = loginService.exchangeCodeForAccessToken(code);
		if (accessToken == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to obtain access token");
		}
		System.out.println(accessToken);
		
		String indexUri = "https://www.google.com";
		response.sendRedirect(indexUri);
		
//		// 使用 Access Token 獲取用戶信息，塞到另一個member表???
//		LineProfile profile = loginService.getUserProfile(accessToken);
//		if (profile == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to fetch user profile");
//		}
//
//		// 業務邏輯：如創建用戶或綁定
//		processUser(profile);

//		return ResponseEntity.ok("登入成功，歡迎 " + profile.getDisplayName());
		
		return null;
	}

}
