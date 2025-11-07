package uz.keepsolution.reactiveollamaapi.service.webclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.configuration.property.ReplicateProperties;
import uz.keepsolution.reactiveollamaapi.configuration.webclient.WebClientConfiguration;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientRequestDTO;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientDTO;

@Component
public class ReplicateWebClientService {

    private final WebClient webClient;
    private final ReplicateProperties properties;

    public ReplicateWebClientService(@Qualifier(WebClientConfiguration.REPLICATE_WEB_CLIENT) WebClient webClient, ReplicateProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<ReplicateWebClientDTO> generateImageByImage(ReplicateWebClientRequestDTO dto) {
        return webClient.post()
                .uri("/v1/models/" + properties.getModel() + "/predictions")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ReplicateWebClientDTO.class);
    }

}
