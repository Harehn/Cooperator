package ca.mcgill.ecse321.cooperator9.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        	.allowedOrigins( new String[] { "https://cooperator-backend-9.herokuapp.com",
        	                   "https://cooperator-frontend-009.herokuapp.com",
        	                   "http://127.0.0.1:8087" } )
        	.allowCredentials(true);
    }
}