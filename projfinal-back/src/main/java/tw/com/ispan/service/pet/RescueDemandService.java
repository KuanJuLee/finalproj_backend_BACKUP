//package tw.com.ispan.service.pet;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import tw.com.ispan.domain.pet.forRescue.RescueDemand;
//import tw.com.ispan.repository.pet.forRescue.RescueDemandRepository;
//
//@Service
//@Transactional
//public class RescueDemandService {
//	
//	
//	 @Autowired
//	private RescueDemandRepository rescueDemandRepository;
//	 



                     //後來發現其實可接用轉為findAllById就可吐成 List<RescueDemand>


//	//將前端傳進來的多選List<Integer> [1,2,3]轉換為對應RescueDemand物件，才能塞回rescueCase物件中存進資料庫
//	public List<RescueDemand> frontRescueDemandConvert(List<Integer> rescueDemands){
//		
//		 List<RescueDemand> rescueDemandList = rescueDemands.stream()   //將rescueDemands這個List<Integer> 轉換成一個 Stream，如此可以對列表中的每個元素進行操作
//	                .map(rescueDemandRepository::findById)             //對 Stream 中的每個 (整數) 進行映射，呼叫 rescueDemandRepository.findById 方法
//	                .filter(Optional::isPresent)                       //findById 會回傳一個 Optional<RescueDemand>，過濾 Stream 中的元素，只保留那些 Optional 內部有值的元素。
//	                .map(Optional::get)                               //將 Optional<RescueDemand> 轉換成內部的 RescueDemand 物件。
//	                .toList();                                       //將經過處理的 Stream 轉換回一個 List<RescueDemand>
//		System.out.println(rescueDemandList);
//		 return rescueDemandList;                                    //可返回物件list!!!
//	}
//}
