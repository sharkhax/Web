package com.drobot.web.tag.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TagUtil {

    public static final String SORT_ALPHA_DOWN_IMAGE = new StringBuilder(
            "<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-sort-alpha-down\" ")
            .append("fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">")
            .append("<path fill-rule=\"evenodd\" d=\"M4 2a.5.5 0 0 1 .5.5v11a.5.5 0 0 1-1 0v-11A.5.5 0 0 1 4 2z\"/>")
            .append("<path fill-rule=\"evenodd\" d=\"M6.354 11.146a.5.5 0 0 1 0 .708l-2 2a.5.5 0 0 1-.708 0l-2-2a.5.5 ")
            .append("0 0 1 .708-.708L4 12.793l1.646-1.647a.5.5 0 0 1 .708 0z\"/>")
            .append("<path d=\"M9.664 7l.418-1.371h1.781L12.281 7h1.121l-1.78-5.332h-1.235L8.597 7h1.067zM11 ")
            .append("2.687l.652 2.157h-1.351l.652-2.157H11zM9.027 14h3.934v-.867h-2.645v-.055l2.567-3.719v-.")
            .append("691H9.098v.867h2.507v.055l-2.578 3.719V14z\"/></svg>").toString();
    public static final String SORT_ALPHA_UP_IMAGE = new StringBuilder(
            "<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-sort-alpha-up-alt\" ")
            .append("fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">")
            .append("<path fill-rule=\"evenodd\" d=\"M4 14a.5.5 0 0 0 .5-.5v-11a.5.5 0 0 0-1 0v11a.5.5 0 0 0 .5.5z\"/>")
            .append("<path fill-rule=\"evenodd\" d=\"M6.354 4.854a.5.5 0 0 0 0-.708l-2-2a.5.5 0 0 0-.708 0l-2 ")
            .append("2a.5.5 0 1 0 .708.708L4 3.207l1.646 1.647a.5.5 0 0 0 .708 0z\"/>")
            .append("<path d=\"M9.027 7h3.934v-.867h-2.645v-.055l2.567-3.719v-.691H9.098v.867h2.507v.055L9.027 ")
            .append("6.309V7zm.637 7l.418-1.371h1.781L12.281 14h1.121l-1.78-5.332h-1.235L8.597 14h1.067zM11 ")
            .append("9.687l.652 2.157h-1.351l.652-2.156H11z\"/></svg>").toString();
    public static final String SORT_NUMERIC_DOWN_IMAGE = new StringBuilder(
            "<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-sort-numeric-down\" ")
            .append("fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">")
            .append("<path fill-rule=\"evenodd\" d=\"M4 2a.5.5 0 0 1 .5.5v11a.5.5 0 0 1-1 0v-11A.5.5 0 0 1 4 2z\"/>")
            .append("<path fill-rule=\"evenodd\" d=\"M6.354 11.146a.5.5 0 0 1 0 .708l-2 2a.5.5 0 0 1-.708 ")
            .append("0l-2-2a.5.5 0 0 1 .708-.708L4 12.793l1.646-1.647a.5.5 0 0 1 .708 0z\"/>")
            .append("<path d=\"M12.438 7V1.668H11.39l-1.262.906v.969l1.21-.86h.052V7h1.046zm-2.84 5.82c.054.621.625 ")
            .append("1.278 1.761 1.278 1.422 0 2.145-.98 2.145-2.848 0-2.05-.973-2.688-2.063-2.688-1.125 ")
            .append("0-1.972.688-1.972 1.836 0 1.145.808 1.758 1.719 1.758.69 0 1.113-.351 1.261-.742h.059c.031 ")
            .append("1.027-.309 1.856-1.133 1.856-.43 0-.715-.227-.773-.45H9.598zm2.757-2.43c0 ")
            .append(".637-.43.973-.933.973-.516 0-.934-.34-.934-.98 0-.625.407-1 .926-1 .543 0 .941.375.941 1.008z\"/>")
            .append("</svg>").toString();
    public static final String SORT_NUMERIC_UP_IMAGE = new StringBuilder(
            "<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-sort-numeric-up-alt\" ")
            .append("fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">")
            .append("<path fill-rule=\"evenodd\" d=\"M4 14a.5.5 0 0 0 .5-.5v-11a.5.5 0 0 0-1 0v11a.5.5 0 0 0 .5.5z\"/>")
            .append("<path fill-rule=\"evenodd\" d=\"M6.354 4.854a.5.5 0 0 0 0-.708l-2-2a.5.5 0 0 0-.708 0l-2 2a.5.5 ")
            .append("0 1 0 .708.708L4 3.207l1.646 1.647a.5.5 0 0 0 .708 0z\"/>")
            .append("<path d=\"M9.598 5.82c.054.621.625 1.278 1.761 1.278 1.422 0 2.145-.98 2.145-2.848 ")
            .append("0-2.05-.973-2.688-2.063-2.688-1.125 0-1.972.688-1.972 1.836 0 1.145.808 1.758 1.719 1.758.69 ")
            .append("0 1.113-.351 1.261-.742h.059c.031 1.027-.309 1.856-1.133 1.856-.43 0-.715-.227-.773-.45H9.")
            .append("598zm2.757-2.43c0 .637-.43.973-.933.973-.516 0-.934-.34-.934-.98 0-.625.407-1 .926-1 .543 0 ")
            .append(".941.375.941 1.008zM12.438 14V8.668H11.39l-1.262.906v.969l1.21-.86h.052V14h1.046z\"/>")
            .append("</svg>").toString();
    private static final Logger LOGGER = LogManager.getLogger(TagUtil.class);
    private static final String LOCALE_SPLIT_REGEX = "_";
    private static final String RESOURCE_BUNDLE_NAME = "locale/pagecontent";
    private static final String THREE_DOTS = "…";
    private static final String PAGINATION_BUTTON_STYLE = "background-color: #ffffffb8; color: #000";
    private static final String PAGINATION_ACTIVE_BUTTON_STYLE =
            "background-color: #c4c4c4b8; border-color: #c4c4c4b8; color: #000";
    private static final String PAGINATION_DISABLED_BUTTON_STYLE = "background-color: #ffffffb8; color: #979797";

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
                        createDisabledButton(out, THREE_DOTS);
                        createButton(out, currentPage - 1);
                    }
                    createActiveButton(out, currentPage);
                    if (rightDelta <= 3) {
                        for (int i = currentPage + 1; i <= pagesNumber; i++) {
                            createButton(out, i);
                        }
                    } else {
                        createButton(out, currentPage + 1);
                        createDisabledButton(out, THREE_DOTS);
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

    public static void createTableHeadButton(JspWriter out, String content, String style, String sortTag)
            throws JspException {
        try {
            out.write("<th scope=\"col\">");
            out.write("<button type=\"submit\" style=\"" + style + "\" name=\"requestedSortBy\" value=\""
                    + sortTag + "\">");
            out.write(content);
            out.write("</th>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating table head button");
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

    private static void createDisabledButton(JspWriter out, String content) throws IOException {
        out.write("<li class=\"page-item\">");
        out.write("<button class=\"page-link\" type=\"button\" ");
        out.write(" style=\"" + PAGINATION_DISABLED_BUTTON_STYLE + "\" disabled>");
        out.write(content + "</button></li>");
    }
}
