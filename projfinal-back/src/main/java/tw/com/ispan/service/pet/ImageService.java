package tw.com.ispan.service.pet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import tw.com.ispan.domain.pet.CasePicture;
import tw.com.ispan.repository.pet.CasePictureRepository;

@Service
@Transactional
public class ImageService {

	@Autowired
	private CasePictureRepository casePictureRepository;

	// 暫存路徑
	@Value("${file.tmp-upload-dir}")
	private String tmpUploadDir;

	// 永存路徑
	@Value("${file.final-upload-dir}")
	private String finalUploadDir;

	// 將圖片暫存於暫存資料夾
	public Map<String, String> tmpSaveImage(MultipartFile file) {
		
		
		//組裝返回訊息(檔名、路徑)
		Map<String, String> fileMessage = new HashMap<>();
		
		
		// 在程式啟動時將相對路徑解析為基於專案運行目錄的絕對路徑，System.getProperty("user.dir")可返回當前應用的執行目錄
		Path absolutePath = Paths.get(System.getProperty("user.dir"), tmpUploadDir).toAbsolutePath();

		// 也可使用操作系統的臨時目錄，如果臨時文件只需要短期存儲
		// String tmpDir = System.getProperty("java.io.tmpdir");
		// Path tmpUploadPath = Paths.get(tmpDir, "upload/tmp/pet/images");

		
		// 生成唯一文件名，防止文件名衝突 (圖片名預計取為memberid_caseid，但須要從token抓會員資料才能抓)
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		// 如果暫存目錄不存在則先創建目錄
		if (!Files.exists(absolutePath)) {
			try {
				Files.createDirectories(absolutePath);
			} catch (IOException e) {
				System.out.println("路徑錯誤");
				e.printStackTrace();
			}
		}

		// 將文件路徑組合起來
		Path filePath = absolutePath.resolve(fileName);
		
		// 將上傳的文件內容保存到指定路徑
		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("檔案路徑不存在，上傳失敗");
			e.printStackTrace();
		}
		
		// 最後檢查文件是否順利存儲
		if (!Files.exists(filePath)) {
			System.out.println("文件存儲失敗");
			return null;
		}
		
		//上傳成功則返回檔案相關訊息
		fileMessage.put("fileName", fileName);
		fileMessage.put("tempUrl", filePath.toString());
		fileMessage.put("status", "200");
		fileMessage.put("message", "圖片上傳成功，路徑：" + filePath.toString());
		return fileMessage;
	}

	
	
	
	// 將暫存資料夾中圖片移到永存資料夾
	public List<String> moveImage(List<String> tmpUrls) {
		
		//用來保存新圖片路徑
		List<String> finalUrl = new ArrayList<String>();
		
		//step1 先把圖片一個個從暫存資料夾移到永存資料夾，並返回新圖片路徑
		for (String tmpUrl : tmpUrls) {
	       
			// 定義來源檔案路徑
			Path sourcePath = Paths.get(tmpUrl);
	        
	        // 定義目標檔案路徑（包括目標檔案名稱）
			// 先從路徑中擷取檔案名稱，再將之與轉為專案執行環境絕對路徑的字串拼接，獲得最終目標檔案路徑
			String fileName = sourcePath.getFileName().toString();
			Path absolutePath = Paths.get(System.getProperty("user.dir"), finalUploadDir).toAbsolutePath();
			Path targetPath = absolutePath.resolve(fileName);
	     
	
	        try {
	            // 確保目標目錄存在，否則創建 .getParent()返回檔案目錄而非文件本身
	            if (!Files.exists(targetPath.getParent())) {
	                Files.createDirectories(targetPath.getParent());
	            }
	            // 移動檔案
	            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("檔案已成功移動到：" + targetPath);
	            finalUrl.add(targetPath.toString());
	          
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("檔案移動失敗：" + e.getMessage());
	        }
		}
		System.out.println(finalUrl.toString());
		 return finalUrl;
	}
	
	
	
	//將圖片路徑保存至資料庫中(casePicture表)
	public List<CasePicture> saveImage(List<String> finalUrls) {
		
		//用來返回圖片實體List
		List<CasePicture> casePictures = new ArrayList<>();
		
		
		for (String url : finalUrls) {
			
			CasePicture casePicture = new CasePicture();
			casePicture.setPictureUrl(url);
			CasePicture newCasePicture = casePictureRepository.save(casePicture);   //會返回包含ID的實體
			casePictures.add(newCasePicture);
		}
		
		return casePictures;
	}
	
//	public List<String> saveImage(MultipartFile[] files) throws IOException {
//
//		List<String> savedFileNames = new ArrayList<>();
//
//		for (MultipartFile file : files) {
//
//			// 生成唯一文件名，防止文件名衝突
//			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//
//			// 將文件路徑組合起來
//			Path filePath = Paths.get(tmpUploadDir, fileName);
//
//			// 將上傳的文件內容保存到指定路徑
//			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//			// 將圖片路徑保存於資料庫中
//			CasePicture casePicture = new CasePicture();
//			casePicture.setPictureUrl(filePath.toString());
//			casePictureRepository.save(casePicture);
//
//			// 添加保存的文件名到返回列表中
//			savedFileNames.add(fileName);
//		}
//
//		return savedFileNames; // 返回保存的文件名（通常存到資料庫中，但我們資料表沒有特別存圖片名稱，直接用caseId去找）

	}
