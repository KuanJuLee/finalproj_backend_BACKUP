package tw.com.ispan.dto.pet;

public class RescueSearchCriteria {

	private Integer rescueStateId; // 救援狀態
	private Integer cityId; // 縣市
	private Integer districtAreaId; // 鄉鎮區
	private Integer speciesId; // 物種
	private Integer breedId; // 品種
	private Boolean suspectLost; // 走失標記 (true/false)
	private String Tag; // 特徵
}
