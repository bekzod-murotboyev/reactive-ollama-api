package uz.keepsolution.reactiveollamaapi.service;

import java.util.Objects;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import uz.keepsolution.reactiveollamaapi.configuration.ai.OllamaConfiguration;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.entity.ChatMessageEntity;
import uz.keepsolution.reactiveollamaapi.repository.ChatMessageRepository;

@Service
public class OllamaChatService {
    private final ChatClient ollamaChatClient;
    private final ChatClient mathStructuredChatClient;
    private final BeanOutputConverter<MathStructuredResponseDTO> outputConverter;
    private final ChatMessageRepository messageRepository;

    public OllamaChatService(
            @Qualifier(OllamaConfiguration.OLLAMA_CHAT_CLIENT) ChatClient ollamaChatClient,
            @Qualifier(OllamaConfiguration.MATH_STRUCTURED_CHAT_CLIENT) ChatClient mathStructuredChatClient,
            BeanOutputConverter<MathStructuredResponseDTO> outputConverter,
            ChatMessageRepository messageRepository) {
        this.ollamaChatClient = ollamaChatClient;
        this.mathStructuredChatClient = mathStructuredChatClient;
        this.outputConverter = outputConverter;
        this.messageRepository = messageRepository;
    }

    public Flux<ServerSentEvent<String>> stream(String question) {
        Flux<String> response = ollamaChatClient
                .prompt()
                .user(question)
                .stream()
                .content()
                .publish()
                .autoConnect(2);

        Mono<Void> saver = response
                .reduce(new StringBuilder(), StringBuilder::append)
                .map(StringBuilder::toString)
                .flatMap(responseText -> messageRepository.save(new ChatMessageEntity(question, responseText)))
                .then();

        return response
                .mergeWith(saver.then(Mono.empty()))
                .map(chunk -> ServerSentEvent.builder(chunk).build());
    }


    public Mono<MathStructuredResponseDTO> calculate(String question) {
        return Mono.fromCallable(() -> mathStructuredChatClient
                        .prompt()
                        .user(question)
                        .call()
                        .content())
                .subscribeOn(Schedulers.boundedElastic())
                .filter(Objects::nonNull)
                .map(outputConverter::convert);
    }
}
