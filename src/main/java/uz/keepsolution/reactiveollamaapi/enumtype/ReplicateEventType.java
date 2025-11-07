package uz.keepsolution.reactiveollamaapi.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReplicateEventType {
    START("start"),
    OUTPUT("output"),
    LOGS("logs"),
    COMPLETED("completed");

    private final String value;
}
