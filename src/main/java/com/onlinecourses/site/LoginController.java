package com.onlinecourses.site;

import com.onlinecourses.site.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by user on 04.07.2015.
 */
@Controller
public class LoginController {

    UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping("/loggedin")
    public ModelAndView loggedin(Map<String, Object> model, HttpServletRequest request) {

        if(request.isUserInRole("ADMIN")) {
            return new ModelAndView(new RedirectView("/admin/courses", true, false));
        }
        if(request.isUserInRole("TEACHER")) {
            return new ModelAndView(new RedirectView("/teacher", true, false));
        }
        else {
            return new ModelAndView(new RedirectView("/student", true, false));
        }
    }
    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> model) {
        model.put("loginForm", new LoginForm());
        return new ModelAndView("login");
    }



    public static class LoginForm
    {
        private String username;
        private String password;

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
    }
}