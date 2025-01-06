package tw.com.ispan.projfinal_back.domain.pet;

import jakarta.persistence.*;

@Entity
@Table(name = "CasePicture")
public class CasePicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int casePictureId;

	@Column(name = "pictureUrl", length = 255, nullable = false)
	private String pictureUrl;

	
	//Constructor
	public CasePicture() {
		super();
	}

	public CasePicture(int casePictureId, String pictureUrl) {
		super();
		this.casePictureId = casePictureId;
		this.pictureUrl = pictureUrl;
	}

	// Getters and Setters
	public int getCasePictureId() {
		return casePictureId;
	}

	public void setCasePictureId(int casePictureId) {
		this.casePictureId = casePictureId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
