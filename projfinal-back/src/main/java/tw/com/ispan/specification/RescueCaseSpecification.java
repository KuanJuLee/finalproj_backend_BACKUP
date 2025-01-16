package tw.com.ispan.specification;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import tw.com.ispan.domain.pet.RescueCase;
import tw.com.ispan.domain.pet.forRescue.RescueProgress;
import tw.com.ispan.dto.pet.RescueSearchCriteria;

//此工具類為生成救援案件頁面的查詢條件
public class RescueCaseSpecification {
	
	//此方法目的為返回一個Specification<RescueCase>查詢條件給JpaSpecificationExecutor介面方法使用
	//參數為使用者從前端輸入條件被controller中RescueSearchCriteria接收，丟進來動態生成對應sql語句
    public static Specification<RescueCase> withRescueSearchCriteria(RescueSearchCriteria criteria) {
        
    	//withRescueSearchCriteria方法直接return由lambda表達式生成的Specification<RescueCase>物件 { }內為override toPredicate()的方法體，可返回predicate
    	//利用CriteriaBuilder底下方法可組裝並返回predicate物件
    	return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();    //cb.conjunction()可生成初始條件，表示 SQL 中的 where 1=1，最後返回此物件出去

            
            // 關鍵字查詢 (匹配RescueCase幾乎所有屬性)  
            if (criteria.getKeyword() != null) {
                
            	// 一對多的關聯，需另外先建立 Join 從 RescueCase 到 RescueProgress (會自動找尋對應id的資料)，改從此為基底出發作查詢
            	//SELECT rc.*
            	//FROM RescueCase rc
            	//JOIN RescueProgress rp ON rc.id = rp.rescueCaseId
            	//WHERE rp.content LIKE '%keyword%';
            	Join<RescueCase, RescueProgress> rescueProgressJoin = root.join("rescueProgresses");   
            	
            	//組裝 WHERE (caseTitle LIKE '%<keyword>%' OR species LIKE '%<keyword>%' OR...)
            	Predicate keywordPredicate = cb.or(
                    cb.like(root.get("caseTitle"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("species").get("species"), "%" + criteria.getKeyword() + "%"),   //寫species會返回對應的Species實體，等同自動JOIN species表了，但仍需再找到需查詢的欄位.get("species")
                    cb.like(root.get("breed").get("breed"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("furColor").get("furColor"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("gender"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("sterilization"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("age"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("microChipNumber"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("city").get("city"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("distinctArea").get("distinctAreaName"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("street"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("publicationTime"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("tag"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("rescueReason"), "%" + criteria.getKeyword() + "%"),
                    cb.like(rescueProgressJoin.get("rescueProgresses"), "%" + criteria.getKeyword() + "%")    //改用rescueProgressJoin
                    
                );
            	
            	query.distinct(true);
                predicate = cb.and(predicate, keywordPredicate);  //將不同查詢條件用AND組合  等同WHERE 條件A AND 條件B...
            }

            
            // 特徵查詢
            if (criteria.getTag() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("tag").get("name"), criteria.getTag()));
            }
            
            // 救援狀態
            if (criteria.getRescueStateId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("caseStateId").get("name"), criteria.getRescueStateId()));
            }

            // 縣市
            if (criteria.getCityId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cityId"), criteria.getCityId()));
            }

            // 鄉鎮區
            if (criteria.getDistrictAreaId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("districtId"), criteria.getDistrictAreaId()));
            }

            // 物種
            if (criteria.getSpeciesId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("speciesId"), criteria.getSpeciesId()));   //查表格中外鍵屬性，用root.get("speciesId")即可，不用.get("species)找到實體
            }

            // 品種
            if (criteria.getBreedId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("breedId").get("name"), criteria.getBreedId()));
            }

            // 走失標記
            if (criteria.getSuspectLost() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("suspLost"), criteria.getSuspectLost()));
            }

            return predicate;
        };
    }
}
