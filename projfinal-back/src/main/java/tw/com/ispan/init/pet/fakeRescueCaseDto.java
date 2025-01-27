package tw.com.ispan.init.pet;

import java.util.List;

public class fakeRescueCaseDto {
	private Integer memberId;
	private String caseTitle;
    private BreedDto breed;
    private SpeciesDto species;
    private FurColorDto furColor;
    private CityDto city;
    private AreaDto districtArea;
    private CaseStateDto caseState;
    private String gender;
    private String sterilization;
    private List<CasePictureDto> casePictures;
    private Integer age;
    private Integer microChipNumber;
    private List<RescueDemandDto> rescueDemands;
    private List<CanAffordDto> canAffords;
    private Boolean suspLost;
    private String street;
    private Double latitude;
    private Double longitude;
    private Integer donationAmount;
    private Integer viewCount;
    private Integer follow;
    private String tag;
    private String rescueReason;
    private String caseUrl;
    private Boolean isHidden;

    public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public SpeciesDto getSpecies() {
        return species;
    }

    public void setSpecies(SpeciesDto species) {
        this.species = species;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public AreaDto getDistrictArea() {
        return districtArea;
    }

    public void setDistrictArea(AreaDto districtArea) {
        this.districtArea = districtArea;
    }

    public CaseStateDto getCaseState() {
        return caseState;
    }

    public void setCaseState(CaseStateDto caseState) {
        this.caseState = caseState;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Integer donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRescueReason() {
        return rescueReason;
    }

    public void setRescueReason(String rescueReason) {
        this.rescueReason = rescueReason;
    }

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public BreedDto getBreed() {
        return breed;
    }

    public void setBreed(BreedDto breed) {
        this.breed = breed;
    }

    public FurColorDto getFurColor() {
        return furColor;
    }

    public void setFurColor(FurColorDto furColor) {
        this.furColor = furColor;
    }

    public List<CasePictureDto> getCasePictures() {
        return casePictures;
    }

    public void setCasePictures(List<CasePictureDto> casePictures) {
        this.casePictures = casePictures;
    }

    public List<RescueDemandDto> getRescueDemands() {
        return rescueDemands;
    }

    public void setRescueDemands(List<RescueDemandDto> rescueDemands) {
        this.rescueDemands = rescueDemands;
    }

    public List<CanAffordDto> getCanAffords() {
        return canAffords;
    }

    public void setCanAffords(List<CanAffordDto> canAffords) {
        this.canAffords = canAffords;
    }

}
