package uz.keepsolution.reactiveollamaapi.configuration.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.keepsolution.reactiveollamaapi.configuration.ai.tool.datetime.DateTimeTool;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.utils.AIUtils;

@Configuration(proxyBeanMethods = false)
public class OllamaConfiguration {

    public static final String OLLAMA_CHAT_CLIENT = "ollamaChatClient";
    public static final String MATH_STRUCTURED_CHAT_CLIENT = "mathStructuredChatClient";

    @Bean(OLLAMA_CHAT_CLIENT)
    public ChatClient ollamaChatClient(
            OllamaChatModel chatModel,
            ChatMemory chatMemory,
            DateTimeTool dateTimeTool
    ) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.CHAT_CLIENT_PROMPT_RULE)
                .defaultTools(dateTimeTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean(MATH_STRUCTURED_CHAT_CLIENT)
    public ChatClient mathStructuredChatClient(
            OllamaChatModel chatModel,
            ChatMemory chatMemory,
            DateTimeTool dateTimeTool,
            BeanOutputConverter<MathStructuredResponseDTO> outputConverter
    ) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.MATH_STRUCTURED_PROMPT_RULE)
                .defaultTools(dateTimeTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultOptions(OllamaOptions
                        .builder()
                        .format(outputConverter.getJsonSchemaMap())
                        .build())
                .build();
    }

    @Bean
    public BeanOutputConverter<MathStructuredResponseDTO> mathStructuredOutputConverter() {
        return new BeanOutputConverter<>(MathStructuredResponseDTO.class);
    }

}
