package tw.com.ispan.projfinal_back.domain.pet;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RescueCase")
public class RescueCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "NVARCHAR(30)", name = "caseTitle", nullable = false)
	private String caseTitle;
	
	//映射到member表，雙向多對一
	@ManyToOne()
	@Column(name = "memberId", nullable = false)
	private int memberId;

	@Column(name = "specieId", nullable = false)
	private int specieId;

	@Column(name = "breedId", nullable = false)
	private int breedId;

	@Column(name = "furColorId", nullable = false)
	private int furColorId;

	@Column(columnDefinition = "NVARCHAR(5)", name = "gender")
	private String gender;

	@Column(name = "sterilization", length = 5, nullable = false)
	private String sterilization;

	@Column(name = "age")
	private int age;

	@Column(name = "microChipNumber")
	private int microChipNumber;

	@Column(name = "suspLost")
	private boolean suspLost;

	@Column(name = "cityId", nullable = false)
	private int cityId;

	@Column(name = "distintId", nullable = false)
	private int distintId;

	@Column(name = "street", length = 10, nullable = false)
	private String street;

	@Column(name = "latitude", precision = 10, scale = 8, nullable = false)
	private double latitude;

	@Column(name = "longitude", precision = 11, scale = 8, nullable = false)
	private double longitude;

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

	@Column(name = "rescueStateId", nullable = false)
	private int rescueStateId;

	@Column(name = "rescueReason", columnDefinition = "nvarchar(max)")
	private String rescueReason;

	@Column(name = "caseUrl", length = 255)
	private String caseUrl;
}
