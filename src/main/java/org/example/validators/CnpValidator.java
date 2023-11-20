//package org.example.validators;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class CnpValidator implements ConstraintValidator<ValidCnp, String> {
//    @Override
//    public void initialize(ValidCnp constraintAnnotation) {
//    }
//
//    @Override
//    public boolean isValid(String cnp, ConstraintValidatorContext context) {
//        return cnp != null && cnp.length() == 13;
//    }
//}
