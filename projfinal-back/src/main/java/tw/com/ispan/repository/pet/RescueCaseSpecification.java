import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import tw.com.ispan.domain.pet.RescueCase;

public class RescueCaseSpecification {

    public static Specification<RescueCase> withSearchCriteria(SearchCriteria criteria) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // 關鍵字查詢 (匹配 RescueCase 所有屬性)
            if (criteria.getKeyword() != null) {
                Predicate keywordPredicate = cb.or(
                    cb.like(root.get("caseName"), "%" + criteria.getKeyword() + "%"),
                    cb.like(root.get("description"), "%" + criteria.getKeyword() + "%")
                );
                predicate = cb.and(predicate, keywordPredicate);
            }

            // 救援狀態
            if (criteria.getRescueState() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("caseState").get("name"), criteria.getRescueState()));
            }

            // 縣市
            if (criteria.getCity() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("city"), criteria.getCity()));
            }

            // 鄉鎮區
            if (criteria.getDistrict() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("district"), criteria.getDistrict()));
            }

            // 物種
            if (criteria.getSpecies() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("species").get("name"), criteria.getSpecies()));
            }

            // 品種
            if (criteria.getBreed() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("breed").get("name"), criteria.getBreed()));
            }

            // 走失標記
            if (criteria.getLostFlag() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("lostFlag"), criteria.getLostFlag()));
            }

            return predicate;
        };
    }
}
