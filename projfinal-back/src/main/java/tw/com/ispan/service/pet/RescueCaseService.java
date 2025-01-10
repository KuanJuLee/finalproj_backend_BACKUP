package tw.com.ispan.service.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.repository.pet.RescueCaseRepository;

@Service
@Transactional
public class RescueCaseService {

	@Autowired
	private RescueCaseRepository rescueCaseRepository;

	
	//新增案件-> 手動將傳進來的dto轉回entity
	public RescueCase convertToEntity(RescueCaseDto rescueCaseDto) {
		 RescueCase rescueCase = new RescueCase();
		 
		 
		 //關聯屬性，透過傳進的id尋找到對應物件
		 
	
		 
		 return rescueCase;
	}
	
	
	//insert新增案件: 
	public RescueCase addRescueCase(RescueCase rescueCase) {
		 
		 //要在這member、species、city、distinct、經緯度、等必填資料(notNull)塞進來，才能存進資料庫中
		
		return rescueCase;
	}
	

}
