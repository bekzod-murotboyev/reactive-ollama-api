package uz.keepsolution.reactiveollamaapi.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record VisionResponseDTO(
        @JsonProperty(required = true, value = "product_type") String productType,
        @JsonProperty(required = true, value = "prompt_list") List<String> promptList
) {
}