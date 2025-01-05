package org.hidevelop.coffeecats.model.validator.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLatitude;

public class LatitudeValidator implements ConstraintValidator<ValidLatitude, String> {

    @Override
    public boolean isValid(String valueStr, ConstraintValidatorContext context) {
        if (valueStr == null || valueStr.isEmpty()) {
            return false; // null이거나 비어 있는 값은 유효하지 않음
        }
        try {
            // 입력값을 숫자로 변환
            double value = Double.parseDouble(valueStr) / 10000000.0;
            // 경도 범위 확인
            return value >= 33.1 && value <= 38.61;
        } catch (NumberFormatException e) {
            // 숫자 변환 실패 시 유효하지 않은 값으로 처리
            return false;
        }
    }
}
