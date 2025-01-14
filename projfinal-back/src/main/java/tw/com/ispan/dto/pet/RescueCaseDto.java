package tw.com.ispan.dto.pet;

import java.util.List;

public class RescueCaseDto {

	//想傳資料進入資料庫的類別
	private String caseTitle;
	private Integer speciesId;
	private Integer breedId;
	private Integer furColorId;
	private String gender;
	private String sterilization;
	private Integer age;
	private Integer microChipNumber;
	private Boolean suspLost;
	private Integer cityId;
	private Integer distinctAreaId;
	private String street;
	private String rescueReason;
	private Integer caseStateId;
	
	//傳遞照片id集合，到service中再用id的list去找圖片
	private List<Integer> casePictures;
	private  List<Integer> rescueDemands;
	private  List<Integer> canAffords;
	
	
	//getter & setter
	public String getCaseTitle() {
		return caseTitle;
	}
	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}
	public Integer getSpeciesId() {
		return speciesId;
	}
	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}
	public Integer getBreedId() {
		return breedId;
	}
	public void setBreedId(Integer breedId) {
		this.breedId = breedId;
	}
	public Integer getFurColorId() {
		return furColorId;
	}
	public void setFurColorId(Integer furColorId) {
		this.furColorId = furColorId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSterilization() {
		return sterilization;
	}
	public void setSterilization(String sterilization) {
		this.sterilization = sterilization;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getMicroChipNumber() {
		return microChipNumber;
	}
	public void setMicroChipNumber(Integer microChipNumber) {
		this.microChipNumber = microChipNumber;
	}
	public Boolean getSuspLost() {
		return suspLost;
	}
	public void setSuspLost(Boolean suspLost) {
		this.suspLost = suspLost;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getDistinctAreaId() {
		return distinctAreaId;
	}
	public void setDistinctAreaId(Integer distinctAreaId) {
		this.distinctAreaId = distinctAreaId;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getRescueReason() {
		return rescueReason;
	}
	public void setRescueReason(String rescueReason) {
		this.rescueReason = rescueReason;
	}
	public List<Integer> getCasePictures() {
		return casePictures;
	}
	public void setCasePictures(List<Integer> casePictures) {
		this.casePictures = casePictures;
	}
	public List<Integer> getRescueDemands() {
		return rescueDemands;
	}
	public void setRescueDemands(List<Integer> rescueDemands) {
		this.rescueDemands = rescueDemands;
	}
	public List<Integer> getCanAffords() {
		return canAffords;
	}
	public void setCanAffords(List<Integer> canAffords) {
		this.canAffords = canAffords;
	}
	
	
	public Integer getCaseStateId() {
		return caseStateId;
	}
	public void setCaseStateId(Integer caseStateId) {
		this.caseStateId = caseStateId;
	}
	@Override
	public String toString() {
		return "RescueCaseDto [caseTitle=" + caseTitle + ", speciesId=" + speciesId + ", breedId=" + breedId
				+ ", furColorId=" + furColorId + ", gender=" + gender + ", sterilization=" + sterilization + ", age="
				+ age + ", microChipNumber=" + microChipNumber + ", suspLost=" + suspLost + ", cityId=" + cityId
				+ ", distinctAreaId=" + distinctAreaId + ", street=" + street + ", rescueReason=" + rescueReason
				+ ", caseStateId=" + caseStateId + ", casePictures=" + casePictures + ", rescueDemands=" + rescueDemands
				+ ", canAffords=" + canAffords + "]";
	}
	
	
	
	
}
