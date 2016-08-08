package com.onsemi.hms.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.onsemi.hms.dao.MenuDAO;
import com.onsemi.hms.dao.UserDAO;
import com.onsemi.hms.model.Menu;
import com.onsemi.hms.model.User;
import com.onsemi.hms.model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

public class Interceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interceptor.class);
    String[] args = {};

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpSession currentSession = request.getSession();
        if (!auth.getName().equals("anonymousUser")) {
            LOGGER.info("preHandle|LOGGED_IN - Login ID: {}", auth.getName());
            //User Session
            User user = setUserSession(auth, currentSession);
            //Menu
            setUserMenu(request, user, currentSession);
        } else {
            LOGGER.info("preHandle|LOGGED_OUT - Login ID: {}", auth.getName());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mapAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
    }

    private User setUserSession(Authentication auth, HttpSession currentSession) {
        //User Session
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByLoginId(auth.getName());
        UserSession userSession = new UserSession();
        userSession.setId(user.getId());
        userSession.setLoginId(user.getLoginId());
        userSession.setFullname(user.getFullname());
        userSession.setEmail(user.getEmail());
        userSession.setGroup(user.getGroupId());
        currentSession.setAttribute("userSession", userSession);
        return user;
    }

    private void setUserMenu(HttpServletRequest request, User user, HttpSession currentSession) {
        Locale locale = RequestContextUtils.getLocale(request);
        String currentPath = request.getRequestURI().substring(request.getContextPath().length());
        String path[] = currentPath.split("\\/");
        String parentPath = "/";
        if (path.length != 0) {
            parentPath += path[1];
        }
        String newCurrentPath = currentPath;
        if (path.length > 3) {
            newCurrentPath = "/" + path[1] + "/" + path[2];
        }
        Connection conn = null;
        String menu = ""
                + "<ul class=\"nav nav-pills nav-stacked\">";
        try {
            conn = dataSource.getConnection();
            MenuDAO menuDAO = new MenuDAO(conn);
            List<Menu> parentMenuList = menuDAO.getMenuAccess("0", parentPath, user.getGroupId());
            for (int i = 0; i < parentMenuList.size(); i++) {
                Menu parentMenu = parentMenuList.get(i);
                List<Menu> childMenuList = menuDAO.getMenuAccess(parentMenu.getCode(), newCurrentPath, user.getGroupId());
                String li = "<li " + parentMenu.getClassActive() + ">";
                String aHref = "<a href=\"" + request.getContextPath() + parentMenu.getUrlPath() + "\">";
                String caret = "";
                if (!childMenuList.isEmpty()) {
                    if (!parentMenu.getClassActive().equals("")) {
                        li = "<li class=\"dropdown active\">";
                    } else {
                        li = "<li class=\"dropdown\">";
                    }
                    aHref = "<a href=\"#\" class=\"dropdown-toggle\">";
                    caret = "<i class=\"fa fa-chevron-circle-down drop-icon\"></i>";
                }
                menu += li
                        + aHref
                        + "<i class=\"fa " + parentMenu.getIcon() + "\"></i>"
                        //+ "<span>" + parentMenu.getName() + "</span>"
                        + "<span>" + messageSource.getMessage(parentMenu.getLabelKey(), args, locale) + "</span>"
                        + caret
                        + "</a>";
                if (!childMenuList.isEmpty()) {
                    String subNav = "<ul class=\"submenu\">";
                    if (parentPath.equals(parentMenu.getUrlPath())) {
                        subNav = "<ul class=\"submenu\" style=\"display: block;\">";
                    }
                    menu += subNav;
                    menu += childMenu(menuDAO, childMenuList, request, locale, newCurrentPath, parentPath, currentPath);
                    menu += "</ul>";
                }
                menu += "</li>";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        menu += "</ul>";
        currentSession.setAttribute("userMenu", menu);
    }

    private String childMenu(MenuDAO menuDAO, List<Menu> childMenuList, HttpServletRequest request, Locale locale, String currentPath, String parentPath, String oriPath) {
        String subNav = "";
        for (int j = 0; j < childMenuList.size(); j++) {
            Menu childMenu = childMenuList.get(j);
            String subNavActive = "";
            if (!childMenu.getClassActive().equals("")) {
                subNavActive = "class=\"active\"";
            } else {
                if (!menuDAO.menuExist(currentPath)) {
                    if (parentPath.equals(childMenu.getUrlPath())) {
                        subNavActive = "class=\"active\"";
                    } else if (oriPath.equals(childMenu.getUrlPath())) {
                        subNavActive = "class=\"active\"";
                    }
                }
            }
            subNav += "<li>";
            subNav += "<a href=\"" + request.getContextPath() + childMenu.getUrlPath() + "\" " + subNavActive + ">"
                    //+ "<i class=\"fa " + childMenu.getIcon() + "\"></i>"
                    //+ childMenu.getName()
                    + messageSource.getMessage(childMenu.getLabelKey(), args, locale)
                    + "</a>";
            subNav += "</li>";
        }
        return subNav;
    }
}
