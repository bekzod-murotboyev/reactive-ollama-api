package uz.keepsolution.reactiveollamaapi.configuration.webclient;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import uz.keepsolution.reactiveollamaapi.configuration.property.ReplicateProperties;

@Configuration(proxyBeanMethods = false)
public class WebClientConfiguration {

    public static final String REPLICATE_WEB_CLIENT = "replicateWebClient";


    @Bean(REPLICATE_WEB_CLIENT)
    public WebClient webClient(WebClient.Builder builder, ReplicateProperties properties) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(10));

        return builder
                .baseUrl(properties.getBaseUrl())
                .defaultHeaders(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                })
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(authorizationFilterFunction(properties.getApiKey()))
                .build();
    }


    private ExchangeFilterFunction authorizationFilterFunction(String apiKey) {
        return ExchangeFilterFunction.ofRequestProcessor(request -> Mono.defer(() ->
                Mono.just(ClientRequest.from(request)
                        .headers(headers -> headers.setBearerAuth(apiKey))
                        .build())));
    }
}
