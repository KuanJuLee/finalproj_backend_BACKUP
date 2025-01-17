package tw.com.ispan.controller.pet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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

	// 暫存路徑
	@Value("${file.tmp-upload-dir}")
	private String tmpUploadDir;

	// 圖片上傳先進到暫存資料夾upload/tmp/pet/images，並返回url給前端，等按下新增案件，將圖片移轉到永存資料夾(新url)，並將案件和圖片數據交給新增案件controller
	@PostMapping("/uploadImage")
	public ResponseEntity<Map<String, String>> uploadImage(@RequestHeader("Authorization") String token,
			@RequestPart("file") MultipartFile file) throws IOException {

		Map<String, String> response = new HashMap<>();

		// 1. 驗證token

		// 2. 驗證圖檔格式
		if (file.isEmpty()) {
			response.put("status", "400");
			response.put("message", "檔案不得為空");
			return ResponseEntity.badRequest().body(response); // spring內建ResponseEntity類別，可創建http回應

		}
		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) { // image開頭包含image/jpeg、image/png、image/gif
			response.put("status", "400");
			response.put("message", "檔案格式只能為圖片");
			return ResponseEntity.badRequest().body(response);
		}

		// 3.存到暫存資料夾(路徑或URL返回到前端，作為一個標識，接著再從前端傳給後端新增案件controller使用)

		// 在程式啟動時將相對路徑解析為基於專案運行目錄的絕對路徑，System.getProperty("user.dir")可返回當前應用的執行目錄。
		Path absolutePath = Paths.get(System.getProperty("user.dir"), tmpUploadDir).toAbsolutePath();

		// 也可使用操作系統的臨時目錄，如果臨時文件只需要短期存儲
		// String tmpDir = System.getProperty("java.io.tmpdir");
		// Path tmpUploadPath = Paths.get(tmpDir, "upload/tmp/pet/images");

		// 生成唯一文件名，防止文件名衝突 (圖片名取為memberid_caseid，但須要從token抓會員資料才能抓)
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		// 如果不存在則先創建目錄
		if (!Files.exists(absolutePath)) {
			Files.createDirectories(absolutePath);
		}

		// 將文件路徑組合起來
		Path filePath = absolutePath.resolve(fileName);

		// 將上傳的文件內容保存到指定路徑
		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			System.getProperty("user.dir");
			response.put("status", "400");
			response.put("message", "此路徑不存在");
			return ResponseEntity.badRequest().body(response);
		}

		// 最後檢查文件是否順利存儲
		if (!Files.exists(filePath)) {
			response.put("status", "500");
			response.put("message", "文件存儲失敗");
			return ResponseEntity.badRequest().body(response);
		}
		
		// 成功訊息(返回臨時url給前端使用，等送出案件再把圖片永存資料夾url存到資料庫中)
		response.put("fileName", fileName);
		response.put("tempUrl", filePath.toString());
		response.put("status", "200");
		response.put("message", "圖片上傳成功，路徑：" + filePath.toString());
		return ResponseEntity.ok(response);

	}
}
