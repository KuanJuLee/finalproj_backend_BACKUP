package tw.com.ispan;
// package tw.com.ispan.projfinal_back;

// import java.net.URI;
// import java.net.URISyntaxException;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.util.UriComponentsBuilder;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonMappingException;

// @SpringBootTest
// public class geocodingServiceTest {

// @Test
// public void testGetCoordinatesFromAddress() throws JsonMappingException,
// JsonProcessingException, URISyntaxException {

// // RestTemplate 在處理 URL 時，會對已經編碼的地址部分再次進行URL編碼，導致%符號本身被編碼為
// %25。這種情況被稱為「重複編碼（double encoding）
// // 為了避免 RestTemplate 重複編碼，應確保 URL 的編碼部分不會再次被處理
// // Spring 提供了 UriComponentsBuilder，它能自動處理 URL 的編碼並避免重複編碼問題

// // 構建 URL
// String address = "新北市蘆洲區長安街99巷";
// String url =
// UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
// .queryParam("address", address)
// .queryParam("key", "AIzaSyBYgeaSJSbLteuiSPtVAgdIV-yPLM4Av-c")
// .queryParam("region", "tw")
// .queryParam("language", "zh-TW")
// .toUriString();

// // 將 URL 轉換為 URI，避免重複編碼
// URI uri = new URI(url);

// HttpHeaders headers = new HttpHeaders();
// headers.set("Referer", "http://localhost:8080");
// headers.add("Accept-Language", "zh-TW");
// HttpEntity<String> entity = new HttpEntity<>(headers);

// RestTemplate restTemplate = new RestTemplate();
// ResponseEntity<String> response = restTemplate.exchange(uri,
// HttpMethod.GET, entity, String.class);

// System.out.println("Response: " + response.getBody());

// // System.out.println("-----------------------------------------");
// // geocodingService.getCoordinatesFromAddress(address);
// // System.out.println(latLng.toString());

// }
// }