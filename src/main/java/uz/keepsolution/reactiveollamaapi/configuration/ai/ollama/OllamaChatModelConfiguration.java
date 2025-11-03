package uz.keepsolution.reactiveollamaapi.configuration.ai.ollama;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.ai.VisionResponseDTO;

@Configuration(proxyBeanMethods = false)
public class OllamaChatModelConfiguration {
    public static final String DEFAULT_CHAT_MODEL = "defaultChatModel";
    public static final String VISION_CHAT_MODEL = "visionChatModel";
    public static final String MATH_STRUCTURED_CHAT_MODEL = "mathStructuredChatModel";

    @Bean(DEFAULT_CHAT_MODEL)
    public OllamaChatModel defaultChatModel(OllamaApi ollamaApi) {
        return OllamaChatModel
                .builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions
                        .builder()
                        .model(OllamaModel.MISTRAL)
                        .build())
                .build();
    }

    @Bean(MATH_STRUCTURED_CHAT_MODEL)
    public OllamaChatModel mathStructuredChatModel(OllamaApi ollamaApi, BeanOutputConverter<MathStructuredResponseDTO> outputConverter) {
        return OllamaChatModel
                .builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions
                        .builder()
                        .model(OllamaModel.MISTRAL)
                        .format(outputConverter.getJsonSchemaMap())
                        .build())
                .build();
    }

    @Bean(VISION_CHAT_MODEL)
    public OllamaChatModel visionChatModel(OllamaApi ollamaApi, BeanOutputConverter<VisionResponseDTO> outputConverter) {
        return OllamaChatModel
                .builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions
                        .builder()
                        .model(OllamaModel.LLAVA)
                        .format(outputConverter.getJsonSchemaMap())
                        .build())
                .build();
    }
}
