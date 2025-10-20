package br.com.firedroid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve qualquer arquivo dentro da pasta "uploads" (e suas subpastas)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // caminho relativo à raiz do projeto
    }
}
