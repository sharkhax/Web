package com.drobot.web.controller.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAccessLevel {
    AccessType[] value() default {AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT, AccessType.GUEST};
}
