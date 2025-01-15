package tw.com.ispan.controller.pet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//三個案件共用的單個圖片上傳
@RestController
@RequestMapping(path = { "/Case" })
public class UploadImageController {
	
	@PostMapping("/uploadImage")
	public ResponseEntity<String> uploadImage(@RequestHeader("Authorization") String token, @RequestPart("file") MultipartFile file) {
	    
		//1. 驗證token
		
		
		
		//2. 驗證+接收圖檔
		if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("圖片檔案為必填");      //badRequest()返回 400 Bad Request，適用於無效的請求
	    }

	    String contentType = file.getContentType();
	    if (contentType == null || !contentType.startsWith("image/")) {     // image開頭包含image/jpeg、image/png、image/gif
	        return ResponseEntity.badRequest().body("只接受圖片檔案");
	    }

	    // 處理圖片檔案，例如儲存到伺服器
	    return ResponseEntity.ok("圖片上傳成功");
	}
}
