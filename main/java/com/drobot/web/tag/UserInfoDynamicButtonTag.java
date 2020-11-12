package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.CommandType;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import com.drobot.web.tag.util.TagUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Custom tag that creates dynamic buttons for user info page.
 */
public class UserInfoDynamicButtonTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(UserInfoDynamicButtonTag.class);
    private static final String BUTTON_STYLE = "width: 90%; color: #495057; text-align: center; font-weight: bold; "
            + "background-color: #ffffffb5; font-family: 'Times New Roman', sans-serif";
    private static final String UNBLOCK_USER_KEY = "userInfo.unblockUser";
    private static final String BLOCK_USER_KEY = "userInfo.blockUser";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.CURRENT_LOCALE);
        ResourceBundle bundle = TagUtil.getMessageBundle(locale);
        createActiveOrBlockedButton(out, session, bundle);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    private void createActiveOrBlockedButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        Map<String, String> userFields = (Map<String, String>) session.getAttribute(SessionAttribute.USER_DATA_FIELDS);
        if (userFields != null) {
            int employeeId = Integer.parseInt(userFields.get(RequestParameter.EMPLOYEE_ID));
            EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
            Entity.Status employeeStatus;
            try {
                employeeStatus = employeeService.findStatus(employeeId).orElseThrow();
            } catch (ServiceException e) {
                LOGGER.log(Level.FATAL, "Error while finding an employee");
                throw new JspException("Error while finding an employee", e);
            }
            if (employeeStatus == Entity.Status.ARCHIVE) {
                LOGGER.log(Level.DEBUG, "Employee status is ARCHIVE, user cannot be unlocked");
            } else {
                Entity.Status userStatus = Entity.Status.valueOf(userFields.get(RequestParameter.USER_STATUS));
                switch (userStatus) {
                    case ACTIVE -> createBlockButton(out, bundle);
                    case BLOCKED -> createUnblockButton(out, bundle);
                    default -> throw new EnumConstantNotPresentException(Entity.Status.class, userStatus.name());
                }
            }
        } else {
            LOGGER.log(Level.ERROR, "User data fields are null");
        }
    }

    private void createUnblockButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
            out.write("name=\"command\" value=\"" + CommandType.UNBLOCK_USER
                    + "\" style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(UNBLOCK_USER_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating unblock button");
            throw new JspException("Error while creating unblock button", e);
        }
    }

    private void createBlockButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
            out.write("name=\"command\" value=\"" + CommandType.BLOCK_USER
                    + "\" style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(BLOCK_USER_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating unblock button");
            throw new JspException("Error while creating unblock button", e);
        }
    }
}
