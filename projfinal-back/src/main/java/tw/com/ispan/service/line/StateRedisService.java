package tw.com.ispan.service.line;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StateRedisService {

	  @Autowired
	    private StringRedisTemplate redisTemplate;

	    // 儲存 state 到 Redis，並設置過期時間
	    public void saveState(String state) {
	        redisTemplate.opsForValue().set(state, "valid", 10, TimeUnit.MINUTES); // 10 分鐘過期
	    }

	    // 驗證 state 是否存在且有效
	    public boolean validateState(String state) {
	        Boolean exists = redisTemplate.hasKey(state);
	        return exists != null && exists;
	    }

	    // 刪除 state（可選）
	    public void deleteState(String state) {
	        redisTemplate.delete(state);
	    }
}
