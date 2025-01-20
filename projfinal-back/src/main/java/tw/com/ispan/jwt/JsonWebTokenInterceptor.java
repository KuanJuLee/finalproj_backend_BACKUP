package tw.com.ispan.jwt;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//攔截特定的 API 請求並驗證 Token
@Component
public class JsonWebTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;

    //此方法為HandlerInterceptor介面底下抽象方法，當每個HTTP請求進入Controller前，Spring會調用這個方法來進行邏輯處理
    //主要作用：
    // 1. 驗證請求是否具有合法的 Token
    // 2. 如果驗證成功，將從 Token 提取的資訊存入 HttpServletRequest 中，以便後續的 Controller 使用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
    	//驗證成功後將 Token 中的資訊（如 custid）存入 HttpServletRequest 的屬性中，供後續 Controller 使用
    	String method = request.getMethod();
        if (!"OPTIONS".equals(method)) {      //OPTIONS 請求通常是瀏覽器的預檢請求，不需要進行驗證
            // 是否有"已登入"的資訊
            String auth = request.getHeader("Authorization");
           
            //如果Heade沒有jwt相關資訊就請求失敗
            if (auth == null || !auth.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            
         // 提取 Token
            String token = auth.substring(7); // 移除 'Bearer '
            String payload = jsonWebTokenUtility.validateToken(token);
            if (payload == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            
         // 提取 memberId
            Integer memberId = jsonWebTokenUtility.extractMemberIdFromPayload(payload);
            if (memberId == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            
            // 將 memberId 添加到請求屬性
            request.setAttribute("memberId", memberId);
            return true;
        }
		return true;
    }
            
//            //會把memberid提取出來
//            JSONObject tokenData = processAuthorizationHeader(auth);
//            if (tokenData == null || tokenData.length() == 0) {
//               // 沒有：是否要阻止使用者呼叫？
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);    //如果解析失敗或返回空資料（無效的 Token 或未提供 Token），回應 HTTP 403 禁止存取
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//                response.setHeader("Access-Control-Allow-Origin", "*");
//                response.setHeader("Access-Control-Allow-Headers", "*");
//
//                return false;
//            }
  
//        request.setAttribute("memberId", custid); // 設置 custid 屬性
//        return true;    //若驗證成功，返回 true，請求將繼續進入 Controller
//    }
    
    
//    //用於解析和驗證 Token，並從中提取用戶資訊
//    private JSONObject processAuthorizationHeader(String auth) throws JSONException {
//       
//    	if (auth != null && auth.length() != 0) {
//            String token = auth.substring(7);  // 移除前綴的'Bearer '，只取 <Token> 部分
//            String data = jsonWebTokenUtility.validateToken(token); //解析token有效性
//            if (data != null && data.length() != 0) {
//            	// 解碼 Token 並提取 custid 
//            	Integer memberId = jsonWebTokenUtility.extractCustIdFromPayload(data);
//            	
//            	JSONObject tokenData = new JSONObject();
//            	
//            	
//               	return tokenData;
//            }
//        }
//        return null;
//    }
}
