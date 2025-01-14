package tw.com.ispan.projfinal_back;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tw.com.ispan.domain.pet.Breed;
import tw.com.ispan.repository.pet.BreedRepository;

@SpringBootTest
public class DataInitializerTests {
	
	@Autowired
	private BreedRepository breedRepository;
	
	@Test
	public void TestCatBreeds() {
	Resource resource = new ClassPathResource("data/catBreeds.json");
    ObjectMapper objectMapper = new ObjectMapper();
	
    try {
    	List<Breed> catBreed = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Breed>>() {});
		catBreed.forEach(breed->breedRepository.save(breed));
		
	} catch (IOException e) {
		System.out.println("資料注入失敗");
		e.printStackTrace();
	}
    
    Resource dogResource = new ClassPathResource("data/dogBreeds.json");
    ObjectMapper objectMapper2 = new ObjectMapper();
	
    try {
    	List<Breed> dogBreed = objectMapper2.readValue(dogResource.getInputStream(), new TypeReference<List<Breed>>() {});
    	dogBreed.forEach(breed->breedRepository.save(breed));
	} catch (IOException e) {
		System.out.println("資料注入失敗");
		e.printStackTrace();
	}
	}
}
