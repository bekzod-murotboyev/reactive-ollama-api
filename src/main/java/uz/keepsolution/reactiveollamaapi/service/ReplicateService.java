package uz.keepsolution.reactiveollamaapi.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.configuration.property.ReplicateProperties;
import uz.keepsolution.reactiveollamaapi.controller.RSocketController;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateGenerateImageResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientDTO;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientRequestDTO;
import uz.keepsolution.reactiveollamaapi.mapper.ReplicateMapper;
import uz.keepsolution.reactiveollamaapi.repository.ReplicateImageRepository;
import uz.keepsolution.reactiveollamaapi.service.webclient.ReplicateWebClientService;
import uz.keepsolution.reactiveollamaapi.utils.RSocketUtils;

@Service
@RequiredArgsConstructor
public class ReplicateService {

    private final OllamaChatService ollamaChatService;
    private final ReplicateWebClientService webClientService;
    private final ReplicateImageRepository repository;
    private final ReplicateMapper mapper;
    private final ReplicateProperties properties;

    private final RSocketController rSocketController;
    private final RSocketRequester requester;

    public Flux<ReplicateGenerateImageResponseDTO> generateImageByImage(FilePart file) {
        return ollamaChatService
                .describeImage(file)
                .flatMapMany(it -> Flux.fromIterable(it.promptList()))
                .concatMap(this::generateAndPersist);
    }

    private Mono<ReplicateGenerateImageResponseDTO> generateAndPersist(String prompt) {
        return requestImage(prompt)
                .flatMap(dto -> repository
                        .save(mapper.toEntity(prompt, dto))
                        .thenReturn(new ReplicateGenerateImageResponseDTO(dto.id(), dto.output() != null && !dto.output().isEmpty() ? dto.output().getFirst() : null))
                );
    }

    private Mono<ReplicateWebClientDTO> requestImage(String prompt) {
        ReplicateWebClientRequestDTO.InputDTO input = new ReplicateWebClientRequestDTO.InputDTO(prompt);
        ReplicateWebClientRequestDTO dto = new ReplicateWebClientRequestDTO(input, properties.getWebhookUrl());
        return webClientService.generateImageByImage(dto);
    }


    public Mono<Void> persistChanges(ReplicateWebClientDTO dto) {
        return repository
                .findById(dto.id())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(entity -> mapper.toEntity(dto, entity))
                .flatMap(repository::save)
                .map(mapper::toDTO)
                .doOnNext(rSocketController::send)
                .then();
    }

    public Mono<Void> test() {
        return rSocketController.send(Mono.just(new ReplicateGenerateImageResponseDTO(UUID.randomUUID().toString(), "https://replicate.delivery/xezq/FDqxvgT96yoFN9UAjaUJegrRdmtf6xAJ3MRbGgHjZiL1gxmVA/tmphj5tpc7u.jpg")));
    }

    public Flux<ReplicateWebClientRequestDTO> get(){
        return requester.route(RSocketUtils.ROUTE_REPLICATE_IMAGE_STREAM).retrieveFlux(ReplicateWebClientRequestDTO.class);
    }
}
