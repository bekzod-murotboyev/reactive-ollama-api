package uz.keepsolution.reactiveollamaapi.dto.replicate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import uz.keepsolution.reactiveollamaapi.enumtype.ReplicateEventType;
import uz.keepsolution.reactiveollamaapi.utils.ReplicateUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReplicateWebClientRequestDTO(
        @JsonProperty("input")
        InputDTO input,
        @JsonProperty("webhook")
        String webhookUrl,
        @JsonProperty("webhook_events_filter")
        List<String> webhookEventsFilter
) {


    public ReplicateWebClientRequestDTO(InputDTO input, String webhookUrl) {
        this(input, webhookUrl, webhookUrl != null ? List.of(ReplicateEventType.COMPLETED.getValue()) : Collections.emptyList());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record InputDTO(
            String size,
            Integer width,
            Integer height,
            String prompt,
            @JsonProperty("enhance_prompt")
            boolean enhancePrompt,
            @JsonProperty("max_images")
            Integer maxImageCount,
            List<String> referenceImageInputList,
            @JsonProperty("aspect_ratio")
            String aspectRatio,
            @JsonProperty("sequential_image_generation")
            String sequentialImageGeneration
    ) {

        public InputDTO(String prompt) {
            this(ReplicateUtils.DEFAULT_SIZE,
                    ReplicateUtils.DEFAULT_WIDTH,
                    ReplicateUtils.DEFAULT_HEIGHT,
                    prompt,
                    ReplicateUtils.DEFAULT_ENHANCE_PROMPT,
                    ReplicateUtils.DEFAULT_MAX_IMAGE_COUNT,
                    Collections.emptyList(),
                    ReplicateUtils.DEFAULT_ASPECT_RATIO,
                    ReplicateUtils.DEFAULT_SEQUENTIAL_IMAGE_GENERATION);
        }
    }

}
