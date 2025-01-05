package org.hidevelop.coffeecats.model.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hidevelop.coffeecats.model.validator.validator.LongitudeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LongitudeValidator.class)
/***
 * ElementType.RECORD_COMPONENT: Java 14부터 도입된 레코드(record)의 컴포넌트에 적용 가능
 * ElementType.PARAMETER: 메서드의 파라미터에 적용
 * ElementType.FIELD: 클래스의 필드에 적용
 */
@Target({ElementType.RECORD_COMPONENT, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLongitude {
    String message() default "경도는 124.5에서 131.87 사이의 값이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
