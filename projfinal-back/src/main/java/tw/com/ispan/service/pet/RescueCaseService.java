package tw.com.ispan.service.pet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.pet.Breed;
import tw.com.ispan.domain.pet.CasePicture;
import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.domain.pet.Species;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.repository.pet.BreedRepository;
import tw.com.ispan.repository.pet.CasePictureRepository;
import tw.com.ispan.repository.pet.FurColorRepository;
import tw.com.ispan.repository.pet.RescueCaseRepository;
import tw.com.ispan.repository.pet.SpeciesRepository;

@Service
@Transactional
public class RescueCaseService {

	@Autowired
	private RescueCaseRepository rescueCaseRepository;
	
	@Autowired
	private SpeciesRepository speciesRepository;
	
	@Autowired
	private BreedRepository breedRepository;
	
	@Autowired
	private CasePictureRepository casePictureRepository;
	
	@Autowired
	private FurColorRepository furColorRepository;
	
	
//	private String caseTitle;
//	private Integer speciesId;
//	private Integer breedId;
//	private Integer furColorId;
//	private String gender;
//	private String sterilization;
//	private Integer age;
//	private Integer microChipNumber;
//	private Boolean suspLost;
//	private Integer cityId;
//	private Integer distinctId;
//	private String street;
//	private String rescueReason;
//	private List<Integer> casePictures;
//	private  List<Integer> rescueDemands;
//	private  List<Integer> canAffords;
	
	
	
	//新增案件-> 手動將傳進來的dto轉entity
	public RescueCase convertToEntity(RescueCaseDto dto) {
		 
		RescueCase rescueCase = new RescueCase();
		 
		//沒有對應的資料，直接塞
		rescueCase.setCaseTitle(dto.getCaseTitle());
		rescueCase.setGender(dto.getGender());
		rescueCase.setSterilization(dto.getSterilization());
		rescueCase.setAge(dto.getAge());
		rescueCase.setMicroChipNumber(dto.getMicroChipNumber());
		rescueCase.setSuspLost(dto.getSuspLost());
		rescueCase.set
		
		//以下傳id進來，找到對應資料再塞回enitity物件中
		//物種
		Optional<Species> result1 = speciesRepository.findById(dto.getSpeciesId());
		if(result1!= null && result1.isPresent()) {
			rescueCase.setSpecies(result1.get());
		}
		
		//品種
		Optional<Breed> result2 = breedRepository.findById(dto.getBreedId());
		if(result2!= null && result2.isPresent()) {
			rescueCase.setBreed(result2.get());
		}
		
		//毛色
		
		
		//city
		
		
		//distinct
		
		
		
		
		//圖片
		List<CasePicture> casePictures = dto.getCasePictureId().stream()
		        .map(id -> casePictureRepository.findById(id)
		
		 
		        		
		        		
		//關聯屬性，透過傳進的id尋找到對應物件塞進去
		 
		
	
		 
		 return rescueCase;
	}
	
	
	//insert新增案件: 
	public RescueCase addRescueCase(RescueCase rescueCase) {
		 
		//id資料庫中自動生成
		//最後把關:要確保member、species、city、distinct、latitude、longitude、publicationTime、lastUpadteTime、caseStateId、rescueReason等必填資料塞進來，才能存進資料庫中
		
		return rescueCase;
	}
	

}
