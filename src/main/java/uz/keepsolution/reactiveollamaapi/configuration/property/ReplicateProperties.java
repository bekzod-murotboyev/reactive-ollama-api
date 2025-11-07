package uz.keepsolution.reactiveollamaapi.configuration.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import uz.keepsolution.reactiveollamaapi.utils.ApiUtils;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.ai.replicate")
public class ReplicateProperties {
    @URL
    String baseUrl;
    String webhookBaseUrl;
    @NotBlank
    String apiKey;
    @NotBlank
    String model;

    public void setWebhookBaseUrl(String webhookBaseUrl) {
        if(webhookBaseUrl==null || webhookBaseUrl.isBlank()){
            return;
        }
        this.webhookBaseUrl= webhookBaseUrl;
    }

    public String getWebhookUrl() {
        if(webhookBaseUrl==null || webhookBaseUrl.isBlank()){
            return null;
        }
        return this.webhookBaseUrl+ ApiUtils.API+ApiUtils.V1+ApiUtils.REPLICATE+ApiUtils.WEBHOOK;
    }
}
