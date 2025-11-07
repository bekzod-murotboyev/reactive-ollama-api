package uz.keepsolution.reactiveollamaapi.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import uz.keepsolution.reactiveollamaapi.entity.ReplicateImageEntity;

public interface ReplicateImageRepository extends R2dbcRepository<ReplicateImageEntity, String> {
}
