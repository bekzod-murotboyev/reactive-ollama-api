package uz.keepsolution.reactiveollamaapi.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorizationType {
    BEARER("Bearer "),
    BASIC("Basic ");
    private final String value;
}
