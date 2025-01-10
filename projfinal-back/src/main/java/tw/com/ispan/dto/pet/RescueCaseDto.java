package tw.com.ispan.dto.pet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.domain.pet.CasePicture;
import tw.com.ispan.domain.pet.City;
import tw.com.ispan.domain.pet.Distinct;
import tw.com.ispan.domain.pet.forRescue.CanAfford;
import tw.com.ispan.domain.pet.forRescue.RescueDemand;

public class RescueCaseDto {

	private String caseTitle;
	private Integer specieId;
	private Integer breedId;
	private Integer furColorId;
	private String gender;
	private String sterilization;
	private Integer age;
	private Integer microChipNumber;
	private Boolean suspLost;
	private Integer cityId;
	private Integer distinctId;
	private String street;
	private String rescueReason;
	private List<CasePicture> casePictures;
	private Set<RescueDemand> rescueDemands = new HashSet<>();
	private Set<CanAfford> canAffords;

}
