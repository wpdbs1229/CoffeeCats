package org.hidevelop.coffeecats.model.validator.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLatitude;

public class LatitudeValidator implements ConstraintValidator<ValidLatitude, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // null이거나 비어 있는 값은 유효하지 않음
        }
        // 위도 범위 확인
        return value >= 33.1 && value <= 38.61;
    }
}
