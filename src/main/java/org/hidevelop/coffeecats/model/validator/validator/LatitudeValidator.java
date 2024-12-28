package org.hidevelop.coffeecats.model.validator.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hidevelop.coffeecats.model.validator.constraint.ValidLatitude;

public class LatitudeValidator implements ConstraintValidator<ValidLatitude, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        return value >= 33.1 && value <= 38.61;
    }
}
