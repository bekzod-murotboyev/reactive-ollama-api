package uz.keepsolution.reactiveollamaapi.configuration.ai.beanoutputconvertor;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.ai.VisionResponseDTO;

@Configuration(proxyBeanMethods = false)
public class BeanOutputConvertorConfiguration {
    @Bean
    public BeanOutputConverter<MathStructuredResponseDTO> mathStructuredOutputConverter() {
        return new BeanOutputConverter<>(MathStructuredResponseDTO.class);
    }


    @Bean
    public BeanOutputConverter<VisionResponseDTO> visionOutputConverter() {
        return new BeanOutputConverter<>(VisionResponseDTO.class);
    }
}
