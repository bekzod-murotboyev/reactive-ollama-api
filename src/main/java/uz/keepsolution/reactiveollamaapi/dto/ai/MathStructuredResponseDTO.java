package uz.keepsolution.reactiveollamaapi.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MathStructuredResponseDTO(
        @JsonProperty(required = true, value = "steps") Steps steps,
        @JsonProperty(required = true, value = "final_answer") String finalAnswer) {

    record Steps(
            @JsonProperty(required = true, value = "items") Items[] items) {

        record Items(
                @JsonProperty(required = true, value = "explanation") String explanation,
                @JsonProperty(required = true, value = "output") String output) {
        }
    }
}
