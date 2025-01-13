	package tw.com.ispan.service;
	
	import java.io.UnsupportedEncodingException;
	import java.net.URLEncoder;
	
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.http.ResponseEntity;
	import org.springframework.stereotype.Service;
	import org.springframework.web.client.RestTemplate;
	
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.JsonMappingException;
	import com.fasterxml.jackson.databind.JsonNode;
	import com.fasterxml.jackson.databind.ObjectMapper;
	
	import tw.com.ispan.util.LatLng;
	
	@Service
	public class GeocodingService {
	
		@Value("${google.api.key}")
		private String apiKey; // 從配置文件中讀取APIKey
	
		public LatLng getCoordinatesFromAddress(String address) throws JsonMappingException, JsonProcessingException {
	
			try {
	
				// 調用 Google Geocoding API
				// 將中文地址轉為"UTF-8"編碼加入url中才能正確傳輸(url中只能有某些特定的字元（如英文字母、數字、-、.、_、~）)
				//URLEncoder.encode 會對地址進行 URL 編碼，但它會將空格編碼為 +，因此手動替換回正確自元%20
				//region=tw：指定地區偏好為台灣，避免模糊匹配到其他地區的地址。 language=zh-TW：指定返回結果為繁體中文。
				System.out.println(apiKey);
				String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
						+ URLEncoder.encode(address, "UTF-8").replace("+", "%20") + "&key=" + apiKey+"&region=tw&language=zh-TW";
				
				System.out.println(apiUrl);
				// RestTemplate是Spring提供的一個簡單的 HTTP 客戶端，用於與外部 API 進行交互作用。
				// getForEntity(String url, Class<T> responseType) 方法用於發送 HTTP GET
				// 請求，並將響應映射為指定的Java 類型
				// 返回一個 ResponseEntity<T> 對象，其中包含 HTTP 狀態碼、響應頭和響應主體。
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
	
				// response.getbody()可獲得返回的經緯度json物件
				// 使用 Jackson 的 ObjectMapper 將 JSON 字串轉換為 JsonNode，方便訪問經緯度數據 (json樹形結構看notion)
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode root = objectMapper.readTree(response.getBody());
				System.out.println(response.getBody());
				System.out.println(root);
				if (root.has("results") && root.get("results").size() > 0) {
					JsonNode location = root.get("results").get(0).get("geometry").get("location");
					double lat = location.get("lat").asDouble();
					double lng = location.get("lng").asDouble();
					return new LatLng(lat, lng);
	
				} else {
					System.out.println("沒有抓到此經緯度");
					return null;
				}
			} catch (UnsupportedEncodingException e) {
				System.err.println("api請求錯誤");
				e.printStackTrace();
			}
			return null;
		}
	
	}