package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import com.drobot.web.tag.UserListTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command for loading user's list.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel(AccessType.ADMIN)
public class UserListCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UserListCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.USER_LIST;
        HttpSession session = request.getSession();
        Integer currentPage = (Integer) session.getAttribute(SessionAttribute.USER_LIST_CURRENT_PAGE);
        String requestedPage = request.getParameter(RequestParameter.LIST_PAGE);
        if (requestedPage == null && currentPage == null) {
            currentPage = 1;
        } else if (requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.USER_LIST_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * UserListTag.ROWS_NUMBER;
        int end = UserListTag.ROWS_NUMBER + start;
        String sortBy = (String) session.getAttribute(SessionAttribute.USER_LIST_SORT_BY);
        String requestedSortBy = request.getParameter(RequestParameter.SORT_BY);
        Boolean reverseSorting = (Boolean) session.getAttribute(SessionAttribute.USER_LIST_REVERSE_SORTING);
        if (reverseSorting == null) {
            reverseSorting = false;
        }
        if (requestedSortBy == null && sortBy == null) {
            sortBy = ColumnName.USER_ID;
        } else if (requestedSortBy != null) {
            if (requestedSortBy.equals(sortBy)) {
                reverseSorting = !reverseSorting;
            } else {
                reverseSorting = false;
            }
            sortBy = requestedSortBy;
        }
        session.setAttribute(SessionAttribute.USER_LIST_SORT_BY, sortBy);
        session.setAttribute(SessionAttribute.USER_LIST_REVERSE_SORTING, reverseSorting);
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            List<User> userList = userService.findAll(start, end, sortBy, reverseSorting);
            session.setAttribute(SessionAttribute.USER_LIST, userList);
            int usersNumber = userService.count();
            session.setAttribute(SessionAttribute.USERS_NUMBER, usersNumber);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        LOGGER.log(Level.INFO, "User list has been got");
        return page;
    }
}
