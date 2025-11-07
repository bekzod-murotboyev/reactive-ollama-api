package uz.keepsolution.reactiveollamaapi.mapper;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateGenerateImageResponseDTO;
import uz.keepsolution.reactiveollamaapi.dto.replicate.ReplicateWebClientDTO;
import uz.keepsolution.reactiveollamaapi.entity.ReplicateImageEntity;

@Component
public class ReplicateMapper {

    public ReplicateImageEntity toEntity(String prompt, ReplicateWebClientDTO dto) {
        return ReplicateImageEntity
                .builder()
                .id(dto.id())
                .prompt(prompt)
                .createdAt(LocalDateTime.now())
                .build()
                .insertable();
    }

    public ReplicateImageEntity toEntity(ReplicateWebClientDTO dto, ReplicateImageEntity entity) {
        if (dto.output() != null && !dto.output().isEmpty()) {
            entity.setCompletedAt(LocalDateTime.now());
            entity.setContentUrl(dto.output().getFirst());
        }
        return entity;
    }

    public Mono<ReplicateGenerateImageResponseDTO> toDTO(ReplicateImageEntity entity) {
        return Mono.just(new ReplicateGenerateImageResponseDTO(entity.getId(), entity.getContentUrl()));
    }
}
