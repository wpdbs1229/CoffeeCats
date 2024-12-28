package org.hidevelop.coffeecats.model.validator.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLongitude;

public class LongitudeValidator implements ConstraintValidator<ValidLongitude, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value==null){
            return false;
        }
        return value >= 124.5 && value <= 131.87;
    }
}
