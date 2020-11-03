package com.drobot.web.tag.util;

import com.drobot.web.controller.RequestParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TagUtil {

    private static final Logger LOGGER = LogManager.getLogger(TagUtil.class);
    private static final String LOCALE_SPLIT_REGEX = "_";
    private static final String RESOURCE_BUNDLE_NAME = "locale/pagecontent";
    private static final String THREE_DOTS = "…";
    private static final String PAGINATION_BUTTON_STYLE = "background-color: #ffffffb8; color: #000";
    private static final String PAGINATION_ACTIVE_BUTTON_STYLE =
            "background-color: #c4c4c4b8; border-color: #c4c4c4b8; color: #000";
    private static final String PAGINATION_DISABLED_BUTTON_STYLE = "background-color: #ffffffb8; color: #979797";
    private static final String SUBMIT_BUTTON_MSG = "table.paginationSubmitButton";

    private TagUtil() {
    }

    public static ResourceBundle getMessageBundle(String stringLocale) {
        String[] localeArr = stringLocale.split(LOCALE_SPLIT_REGEX);
        Locale locale = new Locale(localeArr[0], localeArr[1]);
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
        return bundle;
    }

    public static void createPagination(PageContext pageContext, int currentPage, int pagesNumber,
                                        String command) throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String contextPath = pageContext.getServletContext().getContextPath();
            out.write("<form method=\"post\" action=\"" + contextPath + "/mainController\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"" + command + "\"/>");
            out.write("<nav aria-label=\"pagination\"><ul class=\"pagination\">");
            if (pagesNumber < 0) {
                LOGGER.log(Level.FATAL, "Pages number can not be less than 0");
                throw new IllegalArgumentException("Pages number can not be less than 0");
            }
            if (pagesNumber == 0) {
                createDisabledPreviousButton(out);
                createActiveButton(out, currentPage);
                createDisabledNextButton(out);
            } else {
                createPreviousButton(out, currentPage);
                if (pagesNumber <= 4) {
                    for (int i = 1; i <= pagesNumber; i++) {
                        if (i == currentPage) {
                            createActiveButton(out, currentPage);
                        } else {
                            createButton(out, i);
                        }
                    }
                } else {
                    int rightDelta = pagesNumber - currentPage;
                    int leftDelta = currentPage - 1;
                    if (leftDelta <= 3) {
                        for (int i = 1; i <= currentPage - 1; i++) {
                            createButton(out, i);
                        }
                    } else {
                        createButton(out, 1);
                        createNavigationButton(out, THREE_DOTS);
                        createButton(out, currentPage - 1);
                    }
                    createActiveButton(out, currentPage);
                    if (rightDelta <= 3) {
                        for (int i = currentPage + 1; i <= pagesNumber; i++) {
                            createButton(out, i);
                        }
                    } else {
                        createButton(out, currentPage + 1);
                        createNavigationButton(out, THREE_DOTS);
                        createButton(out, pagesNumber);
                    }
                }
                createNextButton(out, currentPage, pagesNumber);
            }
            out.write("</ul></nav></form>");
            LOGGER.log(Level.DEBUG, "Pagination has been created");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating pagination");
            throw new JspException(e);
        }
    }

    private static void createPreviousButton(JspWriter out, int currentPage) throws IOException {
        if (currentPage == 1) {
            createDisabledPreviousButton(out);
        } else {
            out.write("<li class=\"page-item\">");
            out.write("<button class=\"page-link\" type=\"submit\" name=\"requestedListPage\" ");
            out.write("value=\"" + (currentPage - 1) + "\" aria-label=\"Previous\" style=\""
                    + PAGINATION_BUTTON_STYLE + "\">");
            out.write("<span aria-hidden=\"true\">&laquo;</span></button></li>");
        }
    }

    private static void createDisabledPreviousButton(JspWriter out) throws IOException {
        out.write("<li class=\"page-item disabled\">");
        out.write("<button class=\"page-link\" type=\"submit\" ");
        out.write("aria-label=\"Previous\" aria-disabled=\"true\" style=\""
                + PAGINATION_DISABLED_BUTTON_STYLE + "\">");
        out.write("<span aria-hidden=\"true\">&laquo;</span></button></li>");
    }

    private static void createNextButton(JspWriter out, int currentPage, int pagesNumber) throws IOException {
        if (currentPage == pagesNumber) {
            createDisabledNextButton(out);
        } else {
            out.write("<li class=\"page-item\">");
            out.write("<button class=\"page-link\" type=\"submit\" name=\"requestedListPage\" ");
            out.write("value=\"" + (currentPage + 1) + "\" aria-label=\"Next\" style=\""
                    + PAGINATION_BUTTON_STYLE + "\">");
            out.write("<span aria-hidden=\"true\">&raquo;</span></button></li>");
        }
    }

    private static void createDisabledNextButton(JspWriter out) throws IOException {
        out.write("<li class=\"page-item disabled\">");
        out.write("<button class=\"page-link\" type=\"submit\" ");
        out.write("aria-label=\"Next\" aria-disabled=\"true\" style=\""
                + PAGINATION_DISABLED_BUTTON_STYLE + "\">");
        out.write("<span aria-hidden=\"true\">&raquo;</span></button></li>");
    }

    private static void createButton(JspWriter out, int pageNumber) throws IOException {
        out.write("<li class=\"page-item\">");
        out.write("<button class=\"page-link\" type=\"submit\" name=\"requestedListPage\" ");
        out.write("value=\"" + pageNumber + "\" style=\"" + PAGINATION_BUTTON_STYLE + "\">"
                + pageNumber + "</button></li>");
    }

    private static void createActiveButton(JspWriter out, int pageNumber) throws IOException {
        out.write("<li class=\"page-item active\">");
        out.write("<button class=\"page-link\" style=\"" + PAGINATION_ACTIVE_BUTTON_STYLE + "\" disabled>"
                + pageNumber + "</button></li>");
    }

    private static void createNavigationButton(JspWriter out, String content) throws IOException {
        out.write("<li class=\"page-item\">");
        out.write("<button class=\"page-link\" type=\"button\" ");
        out.write(" style=\"" + PAGINATION_DISABLED_BUTTON_STYLE + "\" disabled>");
        out.write(content + "</button></li>");
    }
}
