package tw.com.ispan.domain.pet;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import tw.com.ispan.domain.admin.Member;
import tw.com.ispan.domain.pet.forRescue.CanAfford;
import tw.com.ispan.domain.pet.forRescue.RescueDemand;
import tw.com.ispan.domain.pet.forRescue.RescueProgress;

@Entity
@Table(name = "AdoptionCase")
public class AdoptionCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adoptionCaseId;

    @Column(name = "caseTitle", columnDefinition = "nvarchar(30)", nullable = false)
    private String caseTitle;

    // 雙向多對一,外鍵,對應member表
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "memberId", nullable = false, foreignKey = @ForeignKey(name = "FK_AdoptionCase_Member"))
    private Member member;
    
	//必填
	// 關聯到species表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "speciesId", nullable = false, foreignKey = @ForeignKey(name = "FK_AdoptionCase_Species"))
	private Species species;
    
	// 關聯到breed表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "breedId", foreignKey = @ForeignKey(name = "FK_AdoptionCase_Breed"))
	private Breed breed;
    
 // 關聯到furColor表，雙向多對一
 	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
 	@JoinColumn(name = "furColorId", foreignKey = @ForeignKey(name = "FK_AdoptionCase_FurColor"))
 	private FurColor furColor;
 	
 	@Column(columnDefinition = "NVARCHAR(5)", name = "gender")
	private String gender;

	@Column(columnDefinition = "NVARCHAR(5)", name = "sterilization")
	private String sterilization;

	@Column(name = "age")
	private Integer age;

	@Column(name = "microChipNumber")
	private Integer microChipNumber;
	
	//必填
	@Column(name = "suspLost", nullable = false)
	private Boolean suspLost;
	
	//必填
	// 關聯到city表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "cityId", nullable = false, foreignKey = @ForeignKey(name = "FK_AdoptionCase_City"))
	private City city;

	//必填
	// 關聯到distinctArea表，雙向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "distinctAreaId", nullable = false, foreignKey = @ForeignKey(name = "FK_AdoptionCase_DistinctArea"))
	private DistinctArea distinctArea;

	@Column(columnDefinition = "NVARCHAR(10)", name = "street")
	private String street;
	
	//必填
	// 10位數，8位小數
	@Column(name = "latitude", precision = 10, nullable = false)
	private Double latitude;
	
	//必填
	// 11位數，8位小數
	@Column(name = "longitude", precision = 11,  nullable = false)
	private Double longitude;

	@Column(name = "donationAmount")
	private Integer donationAmount;

	@Column(name = "viewCount")
	private Integer viewCount;   

	@Column(name = "follow")
	private Integer follow;
	
	//必填
	@Column(name = "publicationTime", nullable = false)
	private LocalDateTime publicationTime;
	
	//必填
	@Column(name = "lastUpdateTime", nullable = false)
	private LocalDateTime lastUpdateTime;
	
	//必填
	// 關聯到CaseState表，單向多對一
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "CaseStateId", nullable = false, foreignKey = @ForeignKey(name = "FK_AdoptionCase_CaseState"))
	private CaseState caseState;
	
	//必填
	@Column(name = "rescueReason", columnDefinition = "nvarchar(max)", nullable = false)
	private String rescueReason;

	@Column(name = "caseUrl", length = 255)
	private String caseUrl;
    
	//必填
	//關聯到CasePicture表，單向一對多，rescueCaseId外鍵會在CasePicture表中
	@OneToMany
	@JoinColumn(name = "rescueCaseId", foreignKey = @ForeignKey(name = "FK_AdoptionCase_RescueCase"))
	private List<CasePicture> casePictures;
	
	//必填
	//和rescueDemand單向多對多
    @ManyToMany
    @JoinTable(
        name = "AdoptionCase_RescueDemand",
        joinColumns = @JoinColumn(name = "adoptionCaseId"),
        inverseJoinColumns = @JoinColumn(name = "rescueDemandId")
    )
    private List<RescueDemand> rescueDemands;
	
    //必填
    //和canAfford表為單向多對多(case找去afford)
    @ManyToMany
    @JoinTable(
        name = "CanAfford_AdoptionCase",
        joinColumns = @JoinColumn(name = "adoptionCaseId"),
        inverseJoinColumns = @JoinColumn(name = "canAffordId")
    )
    private List<CanAfford> canAffords;
    
    
    //和RescueProgress表單向一對多(case找去RescueProgress)
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "adoptionCaseId")
    private List<RescueProgress> rescueProgresses;
	
    
    @OneToMany(mappedBy = "adoptionCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> follows;
    
    
    // 關聯到ReportCase表，單向一對多
    @OneToMany(mappedBy = "adoptionCase", cascade = CascadeType.ALL)
    private List<ReportCase> reportCases; 

}