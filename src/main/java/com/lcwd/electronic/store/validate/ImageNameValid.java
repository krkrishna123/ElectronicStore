package com.lcwd.electronic.store.validate;

//Annotation for custom logic for ImageNAME

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)//isko alag class me implents krna hoga
public @interface ImageNameValid {
    //yaha message rakhenge,error message dega
    String message() default"Invalid Image Name !!";

    //yaha group rakhenge,represents group of contsraints
    Class<?>[]groups()default{ };

    // yaha payload rakhenge,additional information about annotation

    Class<? extends Payload>[]payload()default{ };

}
