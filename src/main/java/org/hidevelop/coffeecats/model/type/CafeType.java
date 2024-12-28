package org.hidevelop.coffeecats.model.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CafeType {

    STUDY("공부"),
    DESSERT("디저트"),
    DATE("데이트")
    ;

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CafeType fromValue(String value) {
        for (CafeType cafeType : CafeType.values()) {
            if (cafeType.getValue().equalsIgnoreCase(value)) {
                return cafeType;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
