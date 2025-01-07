package tw.com.ispan.projfinal_back.domain.pet.forRescue;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import tw.com.ispan.projfinal_back.domain.pet.RescueCase;

@Entity
@Table(name = "RescueDemand")
public class RescueDemand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rescueDemandId")
	private Integer rescueDemandId;

	@Column(name = "rescueDemand", columnDefinition = "NVARCHAR(10)")
	private String rescueDemand;
	
	//雙向多對多
	@ManyToMany(mappedBy = "rescueDemands")
	private Set<RescueCase> rescueCases = new HashSet<>();

	
	public RescueDemand() {
		super();
	}


	public RescueDemand(Integer rescueDemandId, String rescueDemand, Set<RescueCase> rescueCases) {
		super();
		this.rescueDemandId = rescueDemandId;
		this.rescueDemand = rescueDemand;
		this.rescueCases = rescueCases;
	}


	public Integer getRescueDemandId() {
		return rescueDemandId;
	}


	public void setRescueDemandId(Integer rescueDemandId) {
		this.rescueDemandId = rescueDemandId;
	}


	public String getRescueDemand() {
		return rescueDemand;
	}


	public void setRescueDemand(String rescueDemand) {
		this.rescueDemand = rescueDemand;
	}


	public Set<RescueCase> getRescueCases() {
		return rescueCases;
	}


	public void setRescueCases(Set<RescueCase> rescueCases) {
		this.rescueCases = rescueCases;
	}


	@Override
	public String toString() {
		return "RescueDemand [rescueDemandId=" + rescueDemandId + ", rescueDemand=" + rescueDemand + ", rescueCases="
				+ rescueCases + "]";
	}

	
	
}
