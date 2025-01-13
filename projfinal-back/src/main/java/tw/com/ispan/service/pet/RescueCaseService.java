package tw.com.ispan.service.pet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.pet.Breed;
import tw.com.ispan.domain.pet.CasePicture;
import tw.com.ispan.domain.pet.City;
import tw.com.ispan.domain.pet.Distinct;
import tw.com.ispan.domain.pet.FurColor;
import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.domain.pet.Species;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.repository.pet.BreedRepository;
import tw.com.ispan.repository.pet.CasePictureRepository;
import tw.com.ispan.repository.pet.CityRepository;
import tw.com.ispan.repository.pet.DistinctRepository;
import tw.com.ispan.repository.pet.FurColorRepository;
import tw.com.ispan.repository.pet.RescueCaseRepository;
import tw.com.ispan.repository.pet.SpeciesRepository;
import tw.com.ispan.repository.pet.forRescue.CanAffordRepository;
import tw.com.ispan.repository.pet.forRescue.RescueDemandRepository;
import tw.com.ispan.repository.pet.forRescue.RescueProgressRepository;

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
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private DistinctRepository distinctRepository;
	@Autowired
	private RescueDemandRepository rescueDemandRepository;
	@Autowired
	private RescueProgressRepository rescueProgressRepository;
	@Autowired
	private CanAffordRepository canAffordRepository;
	

	// private List<Integer> casePictures;

	// 新增案件-> 手動將傳進來的dto轉entity
	public RescueCase convertToEntity(RescueCaseDto dto) {
		 
		RescueCase rescueCase = new RescueCase();
		 
		//沒有對應的資料表直接塞
		rescueCase.setCaseTitle(dto.getCaseTitle());
		rescueCase.setGender(dto.getGender());
		rescueCase.setSterilization(dto.getSterilization());
		rescueCase.setAge(dto.getAge());
		rescueCase.setMicroChipNumber(dto.getMicroChipNumber());
		rescueCase.setSuspLost(dto.getSuspLost());
		rescueCase.setStreet(dto.getStreet());
		rescueCase.setRescueReason(dto.getRescueReason());
		
		
		
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
		Optional<FurColor> result3 = furColorRepository.findById(dto.getFurColorId());
		if(result3!= null && result3.isPresent()) {
			rescueCase.setFurColor(result3.get());
		}
		
		//city
		Optional<City> result4 = cityRepository.findById(dto.getCityId());
		if(result4!= null && result4.isPresent()) {
			rescueCase.setCityId(result4.get());
		}
		
		
		//distinct
		Optional<Distinct> result5 = distinctRepository.findById(dto.getDistinctId());
		if(result5!= null && result5.isPresent()) {
			rescueCase.setDistinctId(result5.get());
		}
		
		
		//圖片
		List<CasePicture> casePictures = dto.getCasePictureId().stream()
		        .map(id -> casePictureRepository.findById(id)
		
		
		//rescueDemands
		List<RescueDemand> rescueDemands= rescueDemandRepository.findAllById(dto.getRescueDemands());
        rescueCase.setRescueDemands(rescueDemands);      		
		        		
		//canAffords
		List<CanAfford> canAffords= canAffordRepository.findAllById(dto.getCanAffords());
		rescueCase.setCanAffords(canAffords);
		
		
		 return rescueCase;
	}

	
	// insert新增案件:
	public RescueCase addRescueCase(RescueCase rescueCase) {

		// id資料庫中自動生成
		// 最後把關:要確保member、species、city、distinct、latitude、longitude、publicationTime、lastUpadteTime、caseStateId、rescueReason等必填資料塞進來，才能存進資料庫中

		return rescueCase;
	}

	
	//儲存圖片
	public image 
}
