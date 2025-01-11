package tw.com.ispan.controller.pet;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.shop.ProductBean;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.dto.pet.RescueCaseResponse;
import tw.com.ispan.repository.pet.RescueCaseRepository;

//此controller為會員限定功能
@RestController
@RequestMapping(path = { "/member" })
public class MemberController {

	@Autowired
	private RescueCaseRepository rescueCaseRepository;

	// 新增一筆案件
	@PostMapping
		public RescueCaseResponse add(@RequestBody RescueCaseDto rescueCaseDto) {
		
		 //rescueCaseDto傳進service，RescueCaseResponse回傳給前端
		 RescueCaseResponse response = new RescueCaseResponse();

		 
			//傳進來的資料需要驗證(前端即時驗證一次，後端驗證一次)
			
				ProductBean insert = rescueCaseRepository.create(json);
				if (insert == null) {
					response.setSuccess(false);
					response.setMessage("新增失敗");
					// responseJson.put("success", false);
					// responseJson.put("message", "新增失敗");
				} else {
					response.setSuccess(true);
					response.setMessage("新增成功");
					// responseJson.put("success", true);
					// responseJson.put("message", "新增成功");
				}
			}return response;
	// return responseJson.toString();
}

}
