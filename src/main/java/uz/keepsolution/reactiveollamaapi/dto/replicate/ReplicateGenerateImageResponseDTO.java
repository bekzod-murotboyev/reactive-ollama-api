package uz.keepsolution.reactiveollamaapi.dto.replicate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReplicateGenerateImageResponseDTO(
        String id,
        @JsonProperty("content_url")
        String contentUrl
) {
}
