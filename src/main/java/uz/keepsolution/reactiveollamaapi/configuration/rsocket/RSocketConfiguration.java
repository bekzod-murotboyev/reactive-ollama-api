package uz.keepsolution.reactiveollamaapi.configuration.rsocket;

import java.net.URI;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
public class RSocketConfiguration {

    @Bean
    public RSocketRequester requester(RSocketRequester.Builder builder) {
        return builder.websocket(URI.create("ws://localhost:3232/rsocket"));
    }

    @Bean
    public RSocketStrategiesCustomizer routingExtractor() {
        return builder -> builder
                .metadataExtractorRegistry(registry -> registry.metadataToExtract(MediaType.APPLICATION_JSON, String.class, "route"));
    }

}
