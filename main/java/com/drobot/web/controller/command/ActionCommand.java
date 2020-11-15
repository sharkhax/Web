package com.drobot.web.controller.command;

import com.drobot.web.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface that represents "Command" pattern. Used by a controller.
 *
 * @author Vladislav Drobot
 */
public interface ActionCommand {
    /**
     * Processes a request from controller and returns the page to be redirected.
     *
     * @param request request object from page.
     * @return null if requested page doesn't exist (error 404) or page to be redirected.
     * @throws CommandException if an exception has occurred while executing.
     */
    String execute(HttpServletRequest request) throws CommandException;
}
