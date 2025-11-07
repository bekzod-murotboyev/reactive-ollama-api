package uz.keepsolution.reactiveollamaapi.configuration.ai.ollama;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.ai.model.ollama.autoconfigure.OllamaConnectionDetails;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration(proxyBeanMethods = false)
public class OllamaApiConfiguration {

    public static final String VISION_API = "visionOllamaApi";

    @Bean
    @Primary
    public OllamaApi ollamaApi(OllamaConnectionDetails connectionDetails,
                               ObjectProvider<RestClient.Builder> restClientBuilderProvider,
                               ObjectProvider<WebClient.Builder> webClientBuilderProvider,
                               ResponseErrorHandler responseErrorHandler) {
        return OllamaApi
                .builder()
                .baseUrl(connectionDetails.getBaseUrl())
                .restClientBuilder(restClientBuilderProvider.getIfAvailable(RestClient::builder))
                .webClientBuilder(webClientBuilderProvider.getIfAvailable(WebClient::builder))
                .responseErrorHandler(responseErrorHandler)
                .build();
    }

    @Bean(VISION_API)
    public OllamaApi visionOllamaApi(OllamaConnectionDetails connectionDetails,
                                     ObjectProvider<WebClient.Builder> webClientBuilderProvider,
                                     ResponseErrorHandler responseErrorHandler) {
        return OllamaApi
                .builder()
                .baseUrl(connectionDetails.getBaseUrl())
                .restClientBuilder(RestClient.builder().requestFactory(getVisionApiRequestFactory()))
                .webClientBuilder(webClientBuilderProvider.getIfAvailable(WebClient::builder))
                .responseErrorHandler(responseErrorHandler)
                .build();
    }

    private ClientHttpRequestFactory getVisionApiRequestFactory() {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(HttpClient.newHttpClient());
        requestFactory.setReadTimeout(Duration.ofMinutes(3));
        return requestFactory;
    }

}
