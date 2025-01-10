package tw.com.ispan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tw.com.ispan.domain.pet.Species;
import tw.com.ispan.repository.pet.SpeciesRepository;

@Component
public class DataInitializer implements CommandLineRunner{

	@Autowired
	private  SpeciesRepository speciesRepository;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		//存入物種資料
		if (!speciesRepository.existsById(1)) {
			speciesRepository.save(new Species(1, "dog"));
		}
		if (!speciesRepository.existsById(2)) {
			speciesRepository.save(new Species(2, "cat"));
		}
		
		//存入品種資料
		
		
		//存入city資料
		
		
		//存入distinct資料
		
		
	}
	
	
}
