package tw.com.ispan.projfinal_back.domain.pet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "RescueCase")
public class RescueCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "NVARCHAR(30)", name = "caseTitle", nullable = false)
	private String caseTitle;

	// 關聯到member表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "memberId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_Member"))
	private int memberId;

	// 關聯到species表，單向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "specieId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_Specie"))
	private int specieId;

	// 關聯到breed表，單向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "breedId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_Breed"))
	private int breedId;

	// 關聯到furColor表，單向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "furColorId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_FurColor"))
	private int furColorId;

	@Column(columnDefinition = "NVARCHAR(5)", name = "gender")
	private String gender;

	@Column(columnDefinition = "NVARCHAR(5)", name = "sterilization", nullable = false)
	private String sterilization;

	@Column(name = "age")
	private int age;

	@Column(name = "microChipNumber")
	private int microChipNumber;

	@Column(name = "suspLost")
	private boolean suspLost;

	// 關聯到city表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "cityId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_FurColor"))
	private int cityId;

	// 關聯到distint表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "distintId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_Distint"))
	private int distintId;

	@Column(columnDefinition = "NVARCHAR(10)", name = "street", nullable = false)
	private String street;

	// 10位數，8位小數
	@Column(name = "latitude", precision = 10, scale = 8, nullable = false)
	private BigDecimal latitude;

	// 11位數，8位小數
	@Column(name = "longitude", precision = 11, scale = 8, nullable = false)
	private BigDecimal longitude;

	@Column(name = "donationAmount")
	private int donationAmount;

	@Column(name = "view")
	private int view;

	@Column(name = "follow")
	private int follow;

	@Column(name = "publicationTime", nullable = false)
	private LocalDateTime publicationTime;

	@Column(name = "lastUpdateTime", nullable = false)
	private LocalDateTime lastUpdateTime;

	// 關聯到rescueState表，單向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "rescueStateId", nullable = false, foreignKey = @ForeignKey(name = "FK_RescueCase_RescueState"))
	private int rescueStateId;

	@Column(name = "rescueReason", columnDefinition = "nvarchar(max)")
	private String rescueReason;

	@Column(name = "caseUrl", length = 255)
	private String caseUrl;

	//關聯到CasePicture表，單向一對多，rescueCaseId外鍵會在CasePicture表中
	@OneToMany
	@JoinColumn(name = "rescueCaseId", foreignKey = @ForeignKey(name = "FK_CasePicture_RescueCase"))
	private List<CasePicture> casePictures;
	
	
	// Hibernate 進行實體的初始化需要用到空參建構子
	public RescueCase() {
		super();
	}

	public RescueCase(int id, String caseTitle, int memberId, int specieId, int breedId, int furColorId, String gender,
			String sterilization, int age, int microChipNumber, boolean suspLost, int cityId, int distintId,
			String street, BigDecimal latitude, BigDecimal longitude, int donationAmount, int view, int follow,
			LocalDateTime publicationTime, LocalDateTime lastUpdateTime, int rescueStateId, String rescueReason,
			String caseUrl) {
		super();
		this.id = id;
		this.caseTitle = caseTitle;
		this.memberId = memberId;
		this.specieId = specieId;
		this.breedId = breedId;
		this.furColorId = furColorId;
		this.gender = gender;
		this.sterilization = sterilization;
		this.age = age;
		this.microChipNumber = microChipNumber;
		this.suspLost = suspLost;
		this.cityId = cityId;
		this.distintId = distintId;
		this.street = street;
		this.latitude = latitude;
		this.longitude = longitude;
		this.donationAmount = donationAmount;
		this.view = view;
		this.follow = follow;
		this.publicationTime = publicationTime;
		this.lastUpdateTime = lastUpdateTime;
		this.rescueStateId = rescueStateId;
		this.rescueReason = rescueReason;
		this.caseUrl = caseUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaseTitle() {
		return caseTitle;
	}

	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getSpecieId() {
		return specieId;
	}

	public void setSpecieId(int specieId) {
		this.specieId = specieId;
	}

	public int getBreedId() {
		return breedId;
	}

	public void setBreedId(int breedId) {
		this.breedId = breedId;
	}

	public int getFurColorId() {
		return furColorId;
	}

	public void setFurColorId(int furColorId) {
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMicroChipNumber() {
		return microChipNumber;
	}

	public void setMicroChipNumber(int microChipNumber) {
		this.microChipNumber = microChipNumber;
	}

	public boolean isSuspLost() {
		return suspLost;
	}

	public void setSuspLost(boolean suspLost) {
		this.suspLost = suspLost;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getDistintId() {
		return distintId;
	}

	public void setDistintId(int distintId) {
		this.distintId = distintId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public int getDonationAmount() {
		return donationAmount;
	}

	public void setDonationAmount(int donationAmount) {
		this.donationAmount = donationAmount;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public int getFollow() {
		return follow;
	}

	public void setFollow(int follow) {
		this.follow = follow;
	}

	public LocalDateTime getPublicationTime() {
		return publicationTime;
	}

	public void setPublicationTime(LocalDateTime publicationTime) {
		this.publicationTime = publicationTime;
	}

	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getRescueStateId() {
		return rescueStateId;
	}

	public void setRescueStateId(int rescueStateId) {
		this.rescueStateId = rescueStateId;
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

	public List<CasePicture> getCasePictures() {
		return casePictures;
	}

	public void setCasePictures(List<CasePicture> casePictures) {
		this.casePictures = casePictures;
	}

	
}
