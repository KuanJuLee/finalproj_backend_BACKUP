package tw.com.ispan.domain.pet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Breed")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breedId")
    private Integer breedId;

    @Column(name = "breed", nullable = false, columnDefinition = "NVARCHAR(50)")
    private String breed;

    @OneToMany(mappedBy = "breed", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<LostCase> lostCases;

    @OneToMany(mappedBy = "breed", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RescueCase> rescueCases;
    
    @OneToMany(mappedBy = "breed", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<AdoptionCase> adoptionCases;


    // Getters and Setters
    public Integer getBreedId() {
        return breedId;
    }

    public void setBreedId(Integer breedId) {
        this.breedId = breedId;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public List<RescueCase> getRescueCases() {
        return rescueCases;
    }

    public void setRescueCases(List<RescueCase> rescueCases) {
        this.rescueCases = rescueCases;
    }

    public List<LostCase> getLostCases() {
        return lostCases;
    }

    public void setLostCases(List<LostCase> lostCases) {
        this.lostCases = lostCases;
    }

	@Override
	public String toString() {
		return "Breed [breedId=" + breedId + ", breed=" + breed + ", lostCases=" + lostCases + ", rescueCases="
				+ rescueCases + ", adoptionCases=" + adoptionCases + "]";
	}
    
    
    
}

