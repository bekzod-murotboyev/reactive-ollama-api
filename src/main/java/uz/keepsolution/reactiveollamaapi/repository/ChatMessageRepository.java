package uz.keepsolution.reactiveollamaapi.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import uz.keepsolution.reactiveollamaapi.entity.ChatMessageEntity;

public interface ChatMessageRepository extends R2dbcRepository<ChatMessageEntity, Long> {
}
