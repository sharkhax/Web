package com.drobot.web.controller.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for ActionCommand implementations to set its access level.
 *
 * @author Vladislav Drobot
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAccessLevel {

    /**
     * The access levels of the command.
     *
     * @return access types of the command.
     */
    AccessType[] value() default {AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT, AccessType.GUEST};
}
