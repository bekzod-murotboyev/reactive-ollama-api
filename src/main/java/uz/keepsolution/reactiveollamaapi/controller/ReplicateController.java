package uz.keepsolution.reactiveollamaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateGenerateImageResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientDTO;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientRequestDTO;
import uz.keepsolution.reactiveollamaapi.service.ReplicateService;
import uz.keepsolution.reactiveollamaapi.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUtils.API + ApiUtils.V1 + ApiUtils.REPLICATE)
public class ReplicateController {

    private final ReplicateService service;

    @PostMapping(
            value = "/generation",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<ReplicateGenerateImageResponseDTO> generateImageByImage(@RequestPart FilePart file) {
        return service.generateImageByImage(file);
    }

    @PostMapping(ApiUtils.WEBHOOK)
    public Mono<Void> persistChanges(@RequestBody ReplicateWebClientDTO dto) {
        return service.persistChanges(dto);
    }

    @GetMapping("/test")
    public Mono<Void> test() {
        return service.test();
    }

    @GetMapping("/get")
    public Flux<ReplicateWebClientRequestDTO> get() {
        return service.get();
    }


}
