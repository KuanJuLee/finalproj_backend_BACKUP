package tw.com.ispan.controller.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.repository.pet.RescueCaseRepository;

//此為會員限定功能
@RestController
@RequestMapping(path= {"/member"})
public class MemberController {

	@Autowired
	private RescueCaseRepository rescueCaseRepository;
	
	
	//新增案件
	 @PostMapping
	    public CaseResponse create(@RequestBody String json) {
	        // JSONObject responseJson = new JSONObject();
	        ProductResponse response = new ProductResponse();

	        JSONObject obj = new JSONObject(json);
	        Integer id = obj.isNull("id") ? null : obj.getInt("id");

	        if (id == null) {
	            response.setSuccess(false);
	            response.setMessage("id是必要欄位");
	            // responseJson.put("success", false);
	            // responseJson.put("message", "id是必要欄位");
	        } else if (productService.exists(id)) {
	            response.setSuccess(false);
	            response.setMessage("id已存在");
	            // responseJson.put("success", false);
	            // responseJson.put("message", "id已存在");
	        } else {
	            ProductBean insert = productService.create(json);
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
	        }
	        return response;
	        // return responseJson.toString();
	    }
	
}
