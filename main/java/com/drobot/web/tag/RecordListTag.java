package com.drobot.web.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RecordListTag extends TagSupport {

    public static final int ROWS_NUMBER = 10;
    private static final Logger LOGGER = LogManager.getLogger(RecordListTag.class);

    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
