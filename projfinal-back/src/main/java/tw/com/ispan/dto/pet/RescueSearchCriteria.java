package tw.com.ispan.dto.pet;

public class RescueSearchCriteria {

	private String keyword; //關鍵字
	private Integer rescueStateId; // 救援狀態
	private Integer cityId; // 縣市
	private Integer districtAreaId; // 鄉鎮區
	private Integer speciesId; // 物種
	private Integer breedId; // 品種
	private Boolean suspectLost; // 走失標記 (true/false)
	private String Tag; // 特徵
	
	
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getRescueStateId() {
		return rescueStateId;
	}
	public void setRescueStateId(Integer rescueStateId) {
		this.rescueStateId = rescueStateId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getDistrictAreaId() {
		return districtAreaId;
	}
	public void setDistrictAreaId(Integer districtAreaId) {
		this.districtAreaId = districtAreaId;
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
	public Boolean getSuspectLost() {
		return suspectLost;
	}
	public void setSuspectLost(Boolean suspectLost) {
		this.suspectLost = suspectLost;
	}
	public String getTag() {
		return Tag;
	}
	public void setTag(String tag) {
		Tag = tag;
	}
	
	
}
