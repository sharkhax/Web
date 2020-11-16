package com.drobot.web.model.creator.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.impl.UserMapService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

/**
 * Creator implementation used to create an User object.
 *
 * @author Vladislav Drobot
 */
public class UserCreator implements Creator<User> {

    private static final Logger LOGGER = LogManager.getLogger(UserCreator.class);

    @Override
    public Optional<User> create(Map<String, String> fields) {
        Optional<User> result;
        UserMapService userMapService = UserMapService.INSTANCE;
        if (userMapService.isMapValid(fields)) {
            String login = fields.get(RequestParameter.LOGIN);
            String email = fields.get(RequestParameter.EMAIL);
            String stringPosition = fields.get(RequestParameter.EMPLOYEE_POSITION);
            User.Role role;
            switch (stringPosition) {
                case RequestParameter.DOCTOR -> role = User.Role.DOCTOR;
                case RequestParameter.ASSISTANT -> role = User.Role.ASSISTANT;
                default -> throw new EnumConstantNotPresentException(User.Role.class, stringPosition.toUpperCase());
            }
            User user = new User(login, email, role);
            result = Optional.of(user);
        } else {
            result = Optional.empty();
            LOGGER.log(Level.DEBUG, "Some fields are invalid or absent");
        }
        return result;
    }
}
