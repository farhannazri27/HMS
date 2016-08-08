package com.onsemi.hms.controller;

import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.onsemi.hms.model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("userSession")
public class HomeController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    String[] args = {};
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    ServletContext servletContext;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String selectedProgram
    ) {
        HttpSession currentSession = request.getSession();
        UserSession userSession = (UserSession) currentSession.getAttribute("userSession");
        if (userSession != null) {
            //Anything for Dashboard
            return "home/index";
        } else {
            return "home/index";
        }
    }
    
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String loginError(RedirectAttributes redirectAttrs, Locale locale) {
        redirectAttrs.addFlashAttribute("error", messageSource.getMessage("general.label.login.error", args, locale));
        return "redirect:/";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttrs, Locale locale) {
        redirectAttrs.addFlashAttribute("logout", messageSource.getMessage("general.label.logout", args, locale));
        return "redirect:/";
    }
    
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(Model model, HttpServletRequest request) {
        return "home/register";
    }
}
