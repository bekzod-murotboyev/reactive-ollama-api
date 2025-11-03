package uz.keepsolution.reactiveollamaapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import uz.keepsolution.reactiveollamaapi.configuration.ai.ollama.OllamaChatClientConfiguration;
import uz.keepsolution.reactiveollamaapi.dto.ai.MathStructuredResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.ai.VisionResponseDTO;
import uz.keepsolution.reactiveollamaapi.entity.ChatMessageEntity;
import uz.keepsolution.reactiveollamaapi.repository.ChatMessageRepository;

@Service
public class OllamaChatService {
    private final ChatClient streamingChatClient;
    private final ChatClient blockingChatClient;
    private final ChatClient mathStructuredChatClient;
    private final ChatClient visionChatClient;
    private final BeanOutputConverter<VisionResponseDTO> visionOutputConverter;
    private final BeanOutputConverter<MathStructuredResponseDTO> mathStructuredOutputConverter;
    private final ChatMessageRepository messageRepository;

    public OllamaChatService(
            @Qualifier(OllamaChatClientConfiguration.STREAMING_CHAT_CLIENT) ChatClient streamingChatClient,
            @Qualifier(OllamaChatClientConfiguration.BLOCKING_CHAT_CLIENT) ChatClient blockingChatClient,
            @Qualifier(OllamaChatClientConfiguration.MATH_STRUCTURED_CHAT_CLIENT) ChatClient mathStructuredChatClient,
            @Qualifier(OllamaChatClientConfiguration.VISION_CHAT_CLIENT) ChatClient visionChatClient,
            BeanOutputConverter<VisionResponseDTO> visionOutputConverter,
            BeanOutputConverter<MathStructuredResponseDTO> mathStructuredOutputConverter,
            ChatMessageRepository messageRepository) {
        this.streamingChatClient = streamingChatClient;
        this.blockingChatClient = blockingChatClient;
        this.mathStructuredChatClient = mathStructuredChatClient;
        this.visionChatClient = visionChatClient;
        this.visionOutputConverter = visionOutputConverter;
        this.mathStructuredOutputConverter = mathStructuredOutputConverter;
        this.messageRepository = messageRepository;
    }

    public Flux<ServerSentEvent<String>> stream(String question) {
        Flux<String> response = streamingChatClient
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
                .mapNotNull(mathStructuredOutputConverter::convert);
    }

    public Mono<String> blocking(String question) {
        return Mono.fromCallable(() -> blockingChatClient
                        .prompt()
                        .user(question)
                        .call()
                        .content())
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<VisionResponseDTO> describeImage(FilePart file) {
        return Mono.fromCallable(() -> visionChatClient
                        .prompt()
                        .user(spec ->
                                spec
                                        .text("Analyze the image and create a prompt")
                                        .media(Objects.requireNonNull(file.headers().getContentType()), toByteArrayBlocking(file))
                        )
                        .call()
                        .content()
                )
                .subscribeOn(Schedulers.boundedElastic())
                .filter(Objects::nonNull)
                .mapNotNull(visionOutputConverter::convert);
    }

    public ByteArrayResource toByteArrayBlocking(FilePart filePart) {
        DataBuffer buffer = DataBufferUtils.join(filePart.content()).block();
        if (buffer == null) {
            return new ByteArrayResource(new byte[0]);
        }

        try (InputStream inputStream = buffer.asInputStream()) {
            return new ByteArrayResource(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            DataBufferUtils.release(buffer);
        }
    }

}
