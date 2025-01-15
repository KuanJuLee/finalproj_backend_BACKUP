package tw.com.ispan.service.pet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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

	// 從application檔案注入固定路徑
	@Value("${file.upload-dir}")
	private String uploadDir;

	// 將圖片存到外部資料夾+存路徑進到資料庫
	public List<String> saveImage(MultipartFile[] files) throws IOException {

		List<String> savedFileNames = new ArrayList<>();
		for (MultipartFile file : files) {

			// 生成唯一文件名，防止文件名衝突
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

			// 將文件路徑組合起來
			Path filePath = Paths.get(uploadDir, fileName);

			// 將上傳的文件內容保存到指定路徑
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// 將圖片路徑保存於資料庫中
			CasePicture casePicture = new CasePicture();
			casePicture.setPictureUrl(filePath.toString());
			casePictureRepository.save(casePicture);

			
			// 添加保存的文件名到返回列表中
	        savedFileNames.add(fileName);
		}
		
		 return savedFileNames; // 返回保存的文件名（通常存到資料庫中，但我們資料表沒有特別存圖片名稱，直接用caseId去找）

	}
}
