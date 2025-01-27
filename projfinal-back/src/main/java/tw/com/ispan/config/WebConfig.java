package tw.com.ispan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//全局 CORS 配置
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 對所有路徑生效
                .allowedOrigins("http://localhost:5173") // 允許的前端 URL
                .allowedMethods("*") // 允許的請求方法
                .allowedHeaders("*") // 允許的請求頭
                .allowCredentials(true); // 是否允許攜帶憑證
    }
}