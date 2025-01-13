package tw.com.ispan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tw.com.ispan.domain.pet.City;
import tw.com.ispan.domain.pet.FurColor;
import tw.com.ispan.domain.pet.Species;
import tw.com.ispan.repository.pet.CityRepository;
import tw.com.ispan.repository.pet.FurColorRepository;
import tw.com.ispan.repository.pet.SpeciesRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private SpeciesRepository speciesRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private FurColorRepository furColorRepository;

	// 此方法會在專案啟動同時執行一次，進行資料初始化
	@Override
	public void run(String... args) throws Exception {

		
		// 存入物種資料
		if (!speciesRepository.existsById(1)) {
			speciesRepository.save(new Species("狗"));
		}
		if (!speciesRepository.existsById(2)) {
			speciesRepository.save(new Species("貓"));
		}

		// 存入品種資料
		

		//存入毛色資料(主要給米克斯用)
		if (!furColorRepository.existsById(1)) {
			furColorRepository.save(new FurColor("土黃"));
		}
		if (!furColorRepository.existsById(2)) {
			furColorRepository.save(new FurColor("白"));
		}
		if (!furColorRepository.existsById(3)) {
			furColorRepository.save(new FurColor("黑"));
		}
		if (!furColorRepository.existsById(4)) {
			furColorRepository.save(new FurColor("橘白貓"));
		}
		if (!furColorRepository.existsById(5)) {
			furColorRepository.save(new FurColor("三花貓"));
		}
		if (!furColorRepository.existsById(6)) {
			furColorRepository.save(new FurColor("虎斑"));
		}
		if (!furColorRepository.existsById(7)) {
			furColorRepository.save(new FurColor("賓士"));
		}
		
		
		
		
		// 存入city資料
		//臺北市、新北市、基隆市、新竹市、桃園市、新竹縣及宜蘭縣。 中部區域：包括臺中市、苗栗縣、彰化縣、南投縣及雲林縣。 南部區域：包括高雄市、臺南市、嘉義市、嘉義縣、屏東縣及澎湖縣。 東部區域：包括花蓮縣及臺東縣
		if (!cityRepository.existsById(1)) {
			cityRepository.save(new City("臺北市"));
		}
		if (!cityRepository.existsById(2)) {
			cityRepository.save(new City("新北市"));
		}
		if (!cityRepository.existsById(3)) {
			cityRepository.save(new City("基隆市"));
		}
		if (!cityRepository.existsById(4)) {
			cityRepository.save(new City("新竹市"));
		}
		if (!cityRepository.existsById(5)) {
			cityRepository.save(new City("桃園市"));
		}
		if (!cityRepository.existsById(6)) {
			cityRepository.save(new City("新竹縣"));
		}
		if (!cityRepository.existsById(7)) {
			cityRepository.save(new City("宜蘭縣"));
		}
		if (!cityRepository.existsById(8)) {
			cityRepository.save(new City("臺中市"));
		}
		if (!cityRepository.existsById(9)) {
			cityRepository.save(new City("苗栗縣"));
		}
		if (!cityRepository.existsById(10)) {
			cityRepository.save(new City("彰化縣"));
		}
		if (!cityRepository.existsById(11)) {
			cityRepository.save(new City("南投縣"));
		}
		if (!cityRepository.existsById(12)) {
			cityRepository.save(new City("雲林縣"));
		}
		if (!cityRepository.existsById(13)) {
			cityRepository.save(new City("高雄市"));
		}
		if (!cityRepository.existsById(14)) {
			cityRepository.save(new City("臺南市"));
		}
		if (!cityRepository.existsById(15)) {
			cityRepository.save(new City("嘉義市"));
		}
		if (!cityRepository.existsById(16)) {
			cityRepository.save(new City("嘉義縣"));
		}
		if (!cityRepository.existsById(17)) {
			cityRepository.save(new City("屏東縣"));
		}
		if (!cityRepository.existsById(18)) {
			cityRepository.save(new City("澎湖縣"));
		}
		if (!cityRepository.existsById(19)) {
			cityRepository.save(new City("花蓮縣"));
		}
		if (!cityRepository.existsById(20)) {
			cityRepository.save(new City("臺東縣"));
		}
		if (!cityRepository.existsById(21)) {
			cityRepository.save(new City("金門縣"));
		}
		if (!cityRepository.existsById(22)) {
			cityRepository.save(new City("連江縣"));
		}

		
		// 存入distinct資料
		

	}

}
