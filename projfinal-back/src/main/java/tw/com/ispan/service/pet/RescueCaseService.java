package tw.com.ispan.service.pet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import tw.com.ispan.domain.pet.Breed;
import tw.com.ispan.domain.pet.CaseState;
import tw.com.ispan.domain.pet.City;
import tw.com.ispan.domain.pet.DistinctArea;
import tw.com.ispan.domain.pet.FurColor;
import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.domain.pet.Species;
import tw.com.ispan.domain.pet.forRescue.CanAfford;
import tw.com.ispan.domain.pet.forRescue.RescueDemand;
import tw.com.ispan.dto.pet.RescueCaseDto;
import tw.com.ispan.jwt.JsonWebTokenUtility;
import tw.com.ispan.repository.pet.BreedRepository;
import tw.com.ispan.repository.pet.CaseStateRepository;
import tw.com.ispan.repository.pet.CityRepository;
import tw.com.ispan.repository.pet.DistinctAreaRepository;
import tw.com.ispan.repository.pet.FurColorRepository;
import tw.com.ispan.repository.pet.RescueCaseRepository;
import tw.com.ispan.repository.pet.SpeciesRepository;
import tw.com.ispan.repository.pet.forRescue.CanAffordRepository;
import tw.com.ispan.repository.pet.forRescue.RescueDemandRepository;
import tw.com.ispan.repository.pet.forRescue.RescueProgressRepository;
import tw.com.ispan.service.GeocodingService;
//import tw.com.ispan.service.JwtService;
import tw.com.ispan.util.LatLng;

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
	private FurColorRepository furColorRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private DistinctAreaRepository distinctAreaRepository;
	@Autowired
	private RescueDemandRepository rescueDemandRepository;
	@Autowired
	private RescueProgressRepository rescueProgressRepository;
	@Autowired
	private CanAffordRepository canAffordRepository;
	@Autowired
	private CaseStateRepository caseStateRepository;
	@Autowired
	private JsonWebTokenUtility jsonWebTokenUtility;

	@Autowired
	private GeocodingService geocodingService;

	// 手動將傳進來的dto轉回entity，才能丟進jpa增刪修方法
	public RescueCase convertToEntity(RescueCaseDto dto) {

		RescueCase rescueCase = new RescueCase();

		// 沒有對應資料表的屬性直接塞
		rescueCase.setCaseTitle(dto.getCaseTitle());
		rescueCase.setGender(dto.getGender());
		rescueCase.setSterilization(dto.getSterilization());
		rescueCase.setAge(dto.getAge());
		rescueCase.setMicroChipNumber(dto.getMicroChipNumber());
		rescueCase.setSuspLost(dto.getSuspLost());
		rescueCase.setStreet(dto.getStreet());
		rescueCase.setRescueReason(dto.getRescueReason());

		// 以下傳id進來，找到對應資料再塞回enitity物件中
		// 物種
		Optional<Species> result1 = speciesRepository.findById(dto.getSpeciesId());
		if (result1 != null && result1.isPresent()) {
			rescueCase.setSpecies(result1.get()); // 是存一個Species物件在內，result1.get()是此物件的地址!
													// 要印出對應的物種必須result1.get().getSpecies()
		}

		// 品種
		Optional<Breed> result2 = breedRepository.findById(dto.getBreedId());
		if (result2 != null && result2.isPresent()) {
			rescueCase.setBreed(result2.get());
		}

		// 毛色
		Optional<FurColor> result3 = furColorRepository.findById(dto.getFurColorId());
		if (result3 != null && result3.isPresent()) {
			rescueCase.setFurColor(result3.get());
		}

		// city
		Optional<City> result4 = cityRepository.findById(dto.getCityId());
		if (result4 != null && result4.isPresent()) {
			rescueCase.setCity(result4.get());
		}

		// distinctArea
		Optional<DistinctArea> result5 = distinctAreaRepository.findById(dto.getDistinctAreaId());
		if (result5 != null && result5.isPresent()) {
			rescueCase.setDistinctArea(result5.get());
		}

		// rescueDemands
		List<RescueDemand> rescueDemands = rescueDemandRepository.findAllById(dto.getRescueDemands());
		rescueCase.setRescueDemands(rescueDemands);
//        rescueDemandService.frontRescueDemandConvert(rescueDemands);

		// canAffords
		List<CanAfford> canAffords = canAffordRepository.findAllById(dto.getCanAffords());
		rescueCase.setCanAffords(canAffords);
		// 不完整的，僅含有新增案件頁面中用戶自填資料，所以要給add繼續使用
		
		//caseState
		Optional<CaseState> result6 = caseStateRepository.findById(dto.getCaseStateId());
		if (result6 != null && result6.isPresent()) {
			rescueCase.setCaseState(result6.get());
		}
		
		
		return rescueCase;
	}

	// insert新增一筆案件到資料庫
	public RescueCase add(RescueCase rescueCase, String token) {

		// id資料庫中自動生成
		// 最後把關確保用戶沒有手動填的member、latitude、longitude、publicationTime、lastUpadteTime、caseStateId、等必填資料塞進來，才能存進資料庫中

//		 從 JWT 中解析出 memberId
//	    try {
//			Integer memberId = jsonWebTokenUtility.getMemberId(token);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// 設置經緯度
		String adress = rescueCase.getCity().getCity() + rescueCase.getDistinctArea().getDistinctAreaName()
				+ rescueCase.getStreet();
		try {
			LatLng latLng = geocodingService.getCoordinatesFromAddress(adress);
			if (latLng != null) {
				rescueCase.setLatitude(latLng.getLat());
				rescueCase.setLongitude(latLng.getLng());
			}
		} catch (JsonProcessingException e) {
			System.out.println("請求座標API失敗");
			e.printStackTrace();
		}

		// 設置發布時間、最後修改時間
		rescueCase.setPublicationTime(LocalDateTime.now());
		rescueCase.setLastUpdateTime(LocalDateTime.now());

		// 設置預設caseState(待救援id為3，用3去把物件查出來再塞進去)
		Optional<CaseState> result = caseStateRepository.findById(3);
		if(result != null && result.isPresent()) {
		rescueCase.setCaseState(result.get());
		}

		if (rescueCaseRepository.save(rescueCase) != null) {
			System.out.println("新增成功");
			return rescueCase;
		}
		System.out.println("新增失敗");
		return null;

	}
	
	
	//修改案件----------------------------------------------------------------------------------------------
	public RescueCase modify(RescueCase rescueCase, Integer id) {
		
		//必須拿這個新物件有的資料去修改舊物件，這樣才能留存經緯度、創建時間等資訊，而不是用新物件直接sqve()這些資訊會空掉
		Optional<RescueCase> result = rescueCaseRepository.findById(id);
		if(result != null && result.isPresent()) {
			
			RescueCase old = result.get();
			
			//以下寫成三元
			if(rescueCase.getCaseTitle() != null) {
				old.setCaseTitle(rescueCase.getCaseTitle());
			} 
			
			
		}
		
		
		
		if (rescueCaseRepository.save(rescueCase) != null) {
			System.out.println("新增成功");
			return rescueCase;
		}
		System.out.println("新增失敗");
		return null;
	}
	
	
	//確認案件是否存在於資料庫中-------------------------------------------------------------------------------------------
	public boolean exists(Integer id) {
		if(id!=null) {
			return rescueCaseRepository.existsById(id);
		}
		return false;
	}
	
	
	//
	
}
