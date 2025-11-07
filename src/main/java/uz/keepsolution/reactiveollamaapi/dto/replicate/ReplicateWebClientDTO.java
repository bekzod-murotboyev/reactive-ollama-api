package uz.keepsolution.reactiveollamaapi.dto.replicate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReplicateWebClientDTO(
        String id,
        @JsonProperty("urls")
        ActionUrl actionUrl,
        List<String> output
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ActionUrl(
            String cancel,
            String get,
            String stream,
            String web
    ) {
    }
}
