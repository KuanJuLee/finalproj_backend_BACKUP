package tw.com.ispan.controller.pet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.dto.pet.RescueCaseResponse;
import tw.com.ispan.service.pet.ImageService;
import tw.com.ispan.service.pet.RescueCaseService;

//此controller為會員限定功能
@RestController
@RequestMapping(path = { "/member" })
public class MemberRescueController {

	@Autowired
	private RescueCaseService rescueCaseService;
	
	@Autowired
	private ImageService imageService;


	// 新增一筆救援案件
	@PostMapping(path= {"/addRescueCase"})
	public RescueCaseResponse addRescueCase(@RequestHeader("Authorization") String token, @RequestBody RescueCaseDto rescueCaseDto, @RequestPart MultipartFile file) {
		
		 //專案使用JWT(JSON Web Token)來管理會員登入，則可以從前端傳入的 JWT 中提取重要資訊
		 //rescueCaseDto傳進service存資料，RescueCaseResponse回傳給前端
		 //MultipartFile接收圖片
		 
		//傳進來的資料需要驗證(前端即時驗證一次，後端驗證一次)
		//這裡要驗證什麼???
		 
		 //先轉為實體類別後，把該存的放進去(發布時間等..)再存入資料庫中
		 RescueCase rescueCase = rescueCaseService.convertToEntity(rescueCaseDto);
		 RescueCase result = rescueCaseService.addRescueCase(rescueCase, token);
		 
		//圖片存入本地+資料庫中
		 try {
			imageService.saveImage(file);
		} catch (IOException e) {
			System.out.println("圖片儲存失敗");
			e.printStackTrace();
		}
		 
		
		//組裝返回訊息 
		 RescueCaseResponse response = new RescueCaseResponse();
		 
		 
		 
//				ProductBean insert = rescueCaseRepository.create(json);
//				if (insert == null) {
//					response.setSuccess(false);
//					response.setMessage("新增失敗");
//					// responseJson.put("success", false);
//					// responseJson.put("message", "新增失敗");
//				} else {
//					response.setSuccess(true);
//					response.setMessage("新增成功");
//					// responseJson.put("success", true);
//					// responseJson.put("message", "新增成功");
//				}
//			}return response;
	// return responseJson.toString();
		 return response;
}

	// 修改救援案件
//	@PutMapping(path= {"/modifiedRescueCase/{id}"})
//	public RescueCaseResponse modifiedRescueCase(@RequestHeader("Authorization") String token, @RequestBody RescueCaseDto rescueCaseDto, @RequestPart MultipartFile file) {
//		
//	}
	
	
	
	// 刪除救援案件
	
	
}
