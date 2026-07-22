package MY_PROJECT.SMART.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Berlaku untuk semua endpoint
                .allowedOrigins("*")  // Izinkan akses dari semua domain
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Method yang diizinkan
                .allowedHeaders("*")  // Semua header diizinkan
                .allowCredentials(false);  // Tidak perlu kirim cookie
    }
}