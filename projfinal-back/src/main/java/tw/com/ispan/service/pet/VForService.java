package tw.com.ispan.service.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ispan.domain.pet.Breed;
import tw.com.ispan.domain.pet.CaseState;
import tw.com.ispan.domain.pet.City;
import tw.com.ispan.domain.pet.DistrictArea;
import tw.com.ispan.domain.pet.FurColor;
import tw.com.ispan.repository.pet.BreedRepository;
import tw.com.ispan.repository.pet.CaseStateRepository;
import tw.com.ispan.repository.pet.CityRepository;
import tw.com.ispan.repository.pet.DistrictAreaRepository;
import tw.com.ispan.repository.pet.FurColorRepository;

@Service
@Transactional
public class VForService {

    @Autowired
    private FurColorRepository furColorRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private DistrictAreaRepository districtAreaRepository;
    @Autowired
    private BreedRepository BreedRepository;
    @Autowired
    private CaseStateRepository caseStateRepository;

    // 尋找全部furColor
    public List<FurColor> allFurColor() {
        return furColorRepository.findAll();
    }

    // 尋找全部city
    public List<City> allCity() {
        return cityRepository.findAll();
    }

    // 依據cityId尋找對應districtArea
    public List<DistrictArea> getDistrictsByCity(Integer cityId) {
        return districtAreaRepository.findByCity_CityId(cityId);
    }

    // 尋找全部breed
    public List<Breed> allBreed() {
        return BreedRepository.findAll();
    }

    // 尋找全部救援狀態caseStatus
    public List<CaseState> allCaseState() {
        return caseStateRepository.findAll();
    }

    //
}