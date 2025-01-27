package tw.com.ispan.controller.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.pet.Breed;
import tw.com.ispan.domain.pet.CaseState;
import tw.com.ispan.domain.pet.City;
import tw.com.ispan.domain.pet.DistrictArea;
import tw.com.ispan.domain.pet.FurColor;
import tw.com.ispan.service.pet.VForService;

//用於表列對應表中所有資料(物種、縣市、區域等，做下拉選單
@RestController
@RequestMapping(path = { "/pet" })
public class VForController {

    @Autowired
    private VForService vForService;

    // 查詢所有furColor
    @GetMapping("/allFurColor")
    public List<FurColor> allFurColor() {
        System.out.println("查詢所有furColor");
        List<FurColor> furColors = vForService.allFurColor();
        System.out.println(furColors);
        return furColors;
    }

    // 查詢所有City
    @GetMapping("/allCity")
    public List<City> allCity() {
        List<City> cities = vForService.allCity();
        return cities;
    }

    // 依據對應city查詢對應districtArea
    @GetMapping("/districtAreasByCity/{cityId}")
    public List<DistrictArea> getDistrictsByCity(@PathVariable Integer cityId) {
        List<DistrictArea> districtAreas = vForService.getDistrictsByCity(cityId);
        return districtAreas;
    }

    // 查詢所有breed
    @GetMapping("/allBreed")
    public List<Breed> allBreed() {
        List<Breed> breeds = vForService.allBreed();
        return breeds;
    }

    // 查詢所有救援狀態caseStatus
    @GetMapping("/allCaseState")
    public List<CaseState> allCaseState() {
        List<CaseState> caseStatus = vForService.allCaseState();
        return caseStatus;
    }
}
