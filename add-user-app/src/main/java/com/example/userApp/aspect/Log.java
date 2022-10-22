package com.example.userApp.aspect;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to specify the logging of inputs, outputs, timeElapsed.
 * @author idris
 *
 */
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

}