package tw.com.ispan.jwt;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.annotation.PostConstruct;

@Component
public class JsonWebTokenUtility {
	@Value("${jwt.token.expire}")
	private long expire;

	private String issuer = "ispan";
	private byte[] sharedKey; // 用在簽章

	
	@PostConstruct
	public void init() {
		// 需要長度是512-bit的金鑰以便使用HS512演算法
		sharedKey = new byte[64];

		// TODO：可以使用其他方式產生金鑰內容
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(sharedKey);
	}
	
	
	//接收一個 JSON 字串，生成對應的 JWT Token
	public String createToken(String data) {
		Instant now = Instant.now();
		Instant expire = now.plusSeconds(this.expire * 60);
		try {
			// 建立HMAC signer
			JWSSigner signer = new MACSigner(sharedKey);

			// 準備JWT主體
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
					.issuer(issuer)
					.issueTime(Date.from(now))
					.expirationTime(Date.from(expire))
					.subject(data)
					.build();

			// 建立HMAC保護的JWT
			SignedJWT signedJWT = new SignedJWT(
					new JWSHeader(JWSAlgorithm.HS512),
					claimsSet);
			signedJWT.sign(signer);

			// 產生Token
			return signedJWT.serialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//驗證 Token 的有效性，並從中提取資訊。
	public String validateToken(String token) {
		try {
			// 建立HMAC verifier
			JWSVerifier verifier = new MACVerifier(sharedKey);

			// 解析JWS
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

			if (signedJWT.verify(verifier) && new Date().before(claimsSet.getExpirationTime())) {
				String subject = claimsSet.getSubject();
				return subject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//解析token內的memberid
	public Integer extractMemberIdFromPayload(String payload) {
        try {
            // 解碼 Base64 的有效負載
            String decodedPayload = new String(Base64.getDecoder().decode(payload));

            // 解析 JSON，並提取 "memberId" (或其他鍵值名稱)
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(decodedPayload);

            return jsonNode.get("memberId").asInt(); // 提取並返回 custid
        } catch (Exception e) {
            throw new IllegalArgumentException("無法提取 custid", e);
        }
    }	
	
}
