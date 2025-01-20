package tw.com.ispan.controller.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.pet.CasePicture;
import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.dto.pet.ModifyRescueCaseDto;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.dto.pet.RescueCaseResponse;
import tw.com.ispan.dto.pet.RescueSearchCriteria;
import tw.com.ispan.repository.admin.MemberRepository;
import tw.com.ispan.service.MemberService;
import tw.com.ispan.service.pet.ImageService;
import tw.com.ispan.service.pet.RescueCaseService;

//此為救援案件crud
@RestController
@RequestMapping(path = { "/RescueCase" })
public class RescueController {

	@Autowired
	private RescueCaseService rescueCaseService;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private MemberRepository memberRepository;

	// 新增一筆救援案件----------------------------------------------------------------------------------------------------------------------
	@PostMapping(path = { "/add" })
	public RescueCaseResponse add(@RequestHeader("Authorization") String token,
			@RequestAttribute("memberId") Integer memberId, @Validated @RequestBody RescueCaseDto rescueCaseDto) {

		
		// 方法參數: 
		// 1. 專案使用JWT(JSON Web Token)來管理會員登入，則可以從前端傳入的 JWT 中提取重要資訊，且controller必須接收header中token字串
		// 2. @RequestAttribute("memberId")為接收JsonWebTokenInterceptor類別中攔截近來此controller的request，解析token內攜帶的memberId
		// 3. rescueCaseDto傳進service存資料，而RescueCaseResponse回傳訊息給前端
		RescueCaseResponse response = new RescueCaseResponse();

		// 傳進來的資料需要驗證(前端即時驗證一次，後端驗證一次)
		// 1.驗證token(能通過JsonWebTokenInterceptor攔截器即驗證)並拿到會員id，需驗證此id有無在會員資料表中存在
		System.out.println("此為會員id" + memberId + "執行新增案件");
		if (memberId == null) {
			response.setSuccess(false);
			response.setMessage("必須給予會員id");
			return response;
		} else if (!memberRepository.existsById(memberId)) {
			response.setSuccess(false);
			response.setMessage("此會員id不存在於資料中");
			return response;
		}

		
		// 2.驗證必填資料、資料格式(沒寫傳進來dto接收會是預設初始值null或0)->加上@Validated於dto中直接進行驗證，如果驗證失敗，Spring
		// Boot會自動拋出錯誤

		// 3.前端傳圖片暫存url list，先將暫存資料夾中圖片移轉至永存資料夾，操作正確則回傳圖片新路徑，將新路徑存置資料庫中
		List<String> finalUrl = imageService.moveImages(rescueCaseDto.getCasePictures());
		System.out.println("圖片移動完畢!");
		List<CasePicture> casePictures = imageService.saveImage(finalUrl);
			//需要設置檢查為如果圖片增添失敗==案件增添失敗!!!

		// 4. 新增案件至資料庫 先convertToEntity()轉為實體類別後，add()把該存的放進去(圖片、經緯度等..)再存入資料庫中
		RescueCase rescueCaseEntity = rescueCaseService.convertToEntity(rescueCaseDto, memberId);
		RescueCase rescueCase = rescueCaseService.add(rescueCaseEntity, casePictures);

		if (rescueCase != null) {
			// 新增成功
			response.setSuccess(true);
			response.setMessage("新增案件成功");
			return response;
		} else {
			// 新增失敗，如果rescueCase == null
			response.setSuccess(false);
			response.setMessage("新增案件失敗");
			return response;
		}
	}

	// 修改救援案件-----------------------------------------------------------------------------------------------------------------------------
	@PutMapping(path = { "/modify/{id}" })
	public RescueCaseResponse modifiedRescueCase(@PathVariable(name = "id") Integer caseId,
			@RequestHeader("Authorization") String token, @RequestAttribute("memberId") Integer memberId, @Validated @RequestBody ModifyRescueCaseDto dto) {

		// 除了原本新增案件的內容都可修改外，重點是多一個可修改caseState以及會傳imageIdandUrl進來，因此相較新增案件的這兩個屬性不是null
		// 案件id要從前端點選修改按鈕(按鈕做成超連結)時同時送出，因此id即藏在超連結送出的request line裡

		RescueCaseResponse response = new RescueCaseResponse();
		
		// 前端在看到某案件內頁面時，只有當此案件的memberId有對應上自己的，才會在前端看到「編輯此案件」的按鈕，才能進到可以按下此controller修改案就的按鈕
		// 1.驗證token(能通過JsonWebTokenInterceptor攔截器即驗證)並拿到會員id，需驗證此id有無在會員資料表中存在之餘，驗證此案件真的是這個會員po的，他才能修改
		System.out.println("此為會員id" + memberId + "執行修改案件");
		if (memberId == null) {
			response.setSuccess(false);
			response.setMessage("必須給予會員id");
			return response;
		} else if (!memberRepository.existsById(memberId)) {
			response.setSuccess(false);
			response.setMessage("此會員id不存在於資料中");
			return response;
		}
		
		
		// 2. 驗證此案件資料中的memberId真的有對應上此使用者的memberId
		//如果不匹配
		if(!rescueCaseService.iCanModify(memberId , caseId)) {
			response.setSuccess(false);
			response.setMessage("此會員不可修改此案件");
			return response;
		}

		// 3.驗證必填資料、資料格式(沒寫傳進來dto接收會是預設初始值null或0)->加上@Validated於dto中直接進行驗證，如果驗證失敗，Spring
		// Boot會自動拋出錯誤

		// 4. 驗證此案件id是否存在於資料表中，有存在才繼續往service丟。
		if (caseId == null) {
			response.setSuccess(false);
			response.setMessage("必須給予案件id");
			return response;
		} else if (!rescueCaseService.exists(caseId)) {
			response.setSuccess(false);
			response.setMessage("id不存在於資料中");
			return response;
		}

		// 修改圖片
		// 先判斷getImageIdandUrl中有無不對應的部分(表示圖片被修改)，有修改的才需要被移到永存資料夾，同時修改圖片表中對應id的圖片url為新url
		// 返回對應的CasePicture實體，等等用來存進case物件中
		List<CasePicture> newCasePictures = imageService.saveModify(dto.getImageIdandUrl());
		if (newCasePictures == null) {
			response.setSuccess(false);
			response.setMessage("圖片修改出問題");
			return response;
		}

		// 4. 驗證id存在，就去修改這筆資料
		RescueCase rescueCaseEntity = rescueCaseService.modifyConvertToEntity(dto);
		RescueCase rescueCase = rescueCaseService.modify(rescueCaseEntity, id, newCasePictures);

		if (rescueCase != null) {
			// 修改成功
			response.setSuccess(true);
			response.setMessage("修改案件成功");
			return response;
		} else {
			// 修改失敗，回傳rescueCase == null
			response.setSuccess(false);
			response.setMessage("修改案件失敗");
			return response;
		}
	}

	// 刪除救援案件-----------------------------------------------------------------------------------------------------------------------------
	@DeleteMapping(path = { "/delete/{id}" })
	public RescueCaseResponse deleteRescueCase(@PathVariable("id") Integer id,
			@RequestHeader("Authorization") String token) {

		RescueCaseResponse response = new RescueCaseResponse();

		// 1.驗證token(能通過JsonWebTokenInterceptor攔截器即驗證)

		// 2. 驗證此id是否存在於資料表中，有存在才繼續往service丟去刪除
		if (id == null) {
			response.setSuccess(false);
			response.setMessage("必須給予案件id");
			return response;
		} else if (!rescueCaseService.exists(id)) {
			response.setSuccess(false);
			response.setMessage("id不存在於資料中");
			return response;
		}

		// 若id存在，就去修改這筆資料
		if (rescueCaseService.delete(id)) {
			response.setSuccess(true);
			response.setMessage("案件刪除成功");
			return response;
		} else {
			response.setSuccess(true);
			response.setMessage("案件刪除失敗");
			return response;
		}
	}

	// 查詢救援案件-----------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/search")
	public List<RescueCase> searchRescueCases(@RequestBody RescueSearchCriteria criteria,
			@RequestParam(defaultValue = "0") int page, // 前端沒丟參數就用預設值
			@RequestParam(defaultValue = "10") int size) {
		System.out.println("查詢條件" + criteria.toString());
		Pageable pageable = PageRequest.of(page, size);
		Page<RescueCase> resultPage = rescueCaseService.searchRescueCases(criteria, pageable);
		return resultPage.getContent();
	}

}
