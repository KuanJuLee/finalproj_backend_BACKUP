package tw.com.ispan.controller.pet.forRescue;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.domain.pet.forRescue.RescueProgress;
import tw.com.ispan.dto.pet.RescueProgressDTO;
import tw.com.ispan.repository.pet.RescueCaseRepository;
import tw.com.ispan.service.pet.RescueCaseService;
import tw.com.ispan.service.pet.forRescue.RescueProgressService;


@RestController
@RequestMapping(path = { "/RescueCase/rescueProgress" })
public class RescueProgressController {
	
	
	  @Autowired
	    private RescueProgressService rescueProgressService;
	  @Autowired 
	  private RescueCaseService rescueCaseService;
	  @Autowired
	  private RescueCaseRepository rescueCaseRepository;
	  
	  
	  //新增某案件的救援進度
	  @PostMapping("/add/{rescueCaseId}")
	    public ResponseEntity<String> addRescueProgress(
	            @PathVariable Integer rescueCaseId,
	            @RequestBody RescueProgressDTO progressDTO) {

		  // 先透過 caseID 找到對應的 RescueCase
	        Optional<RescueCase> rescueCaseOptional = rescueCaseRepository.findById(rescueCaseId);
	        if (rescueCaseOptional.isEmpty()) {
	            return ResponseEntity.badRequest().body("找不到對應的救援案件");
	        }

	        RescueCase rescueCase = rescueCaseOptional.get();
		  
		  
	        RescueProgress rescueProgress = new RescueProgress();
	        rescueProgress.setRescueCase(rescueCase);  // 設定關聯案件
	        rescueProgress.setProgressDetail(progressDTO.getProgressDetail());
	        rescueProgress.setCreateTime(LocalDateTime.now()); // 自動填充當前時間
	        
	        //圖片要先轉換為後端路徑(前端會傳來一個字串集合，但限制只會有一張圖)
	        if (progressDTO.getImageUrl() != null && !progressDTO.getImageUrl().isEmpty()) {
	            String progressImage = progressDTO.getImageUrl().get(0); // 確保有元素後才取
	            rescueProgress.setImageUrl(progressImage);
	        }

	        rescueProgressService.addProgress(rescueProgress);

	        return ResponseEntity.ok("進度更新成功");
	    }

	  //根據案件id搜尋該救援進度
	  @GetMapping("/{caseId}")
	    public ResponseEntity<List<RescueProgress>> getProgressByCaseId(@PathVariable Integer caseId) {
	        List<RescueProgress> progressList = rescueProgressService.getProgressByCaseId(caseId);
	        return ResponseEntity.ok(progressList);
	    }
}
