package com.drobot.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag that creates buttons
 */
public class PatientInfoDynamicButtonTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
