package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
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

@CommandAccessLevel(AccessType.ADMIN)
public class UserListCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UserListCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.USER_LIST;
        HttpSession session = request.getSession();
        Integer listPage = (Integer) session.getAttribute(RequestParameter.USER_LIST_CURRENT_PAGE);
        if (listPage == null) {
            listPage = 1;
            session.setAttribute(RequestParameter.USER_LIST_CURRENT_PAGE, listPage);
        }
        int start = (listPage - 1) * 10;
        int length = UserListTag.ROWS_NUMBER + start;
        String sortBy = request.getParameter(RequestParameter.USER_LIST_SORT_BY);
        if (sortBy == null) {
            sortBy = ColumnName.USER_ID;
        }
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            List<User> userList = userService.findAll(start, length, sortBy);
            session.setAttribute(RequestParameter.USER_LIST, userList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        LOGGER.log(Level.DEBUG, "User list has been got");
        return page;
    }
}
