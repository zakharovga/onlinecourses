package com.onlinecourses.site;

import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.User;
import com.onlinecourses.site.services.SubjectService;
import com.onlinecourses.site.services.UserService;
import com.onlinecourses.site.validation.ValidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Created by zakharov_ga on 07.07.2015.
 */
@Controller
public class UserController {

    @Autowired
    UserService usersService;

    @Autowired
    SubjectService subjectService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(Map<String, Object> model) {
        model.put("registerForm", new RegisterForm());
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid RegisterForm form, Errors errors) {
        if(errors.hasErrors()) {
            return new ModelAndView("register");
        }
        if(usersService.exists(form.getEmail())) {
            errors.rejectValue("email", "DuplicateKey.user.email");
            return new ModelAndView("register");
        }

        User user = new User();

        user.setId(form.getId());
        user.setEmail(form.getEmail());
        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setCountry(form.getCountry());
        user.setCity(form.getCity());
        user.setBirthDate(form.getBirthDate());
        user.setDescription(form.getDescription());
        user.setEnabled(true);
        user.setPassword(form.getPassword());

        this.usersService.save(user, "ROLE_STUDENT");

        return new ModelAndView(new RedirectView("/login", true, false));
    }

    @RequestMapping(value = "/admin/teachers/create", method = RequestMethod.GET)
    public ModelAndView createTeacher(Map<String, Object> model, @RequestParam(value = "id", required = false) Integer id) {
        CreateTeacherForm createTeacherForm = new CreateTeacherForm();
        if (id != null) {
            User teacher = usersService.getUserById(id);

            createTeacherForm.setId(teacher.getId());
            createTeacherForm.setEmail(teacher.getEmail());
            createTeacherForm.setLastName(teacher.getLastName());
            createTeacherForm.setFirstName(teacher.getFirstName());
            createTeacherForm.setCountry(teacher.getCountry());
            createTeacherForm.setCity(teacher.getCity());
            createTeacherForm.setPhoneNumber(teacher.getPhoneNumber());
            createTeacherForm.setDescription(teacher.getDescription());

            model.put("createTeacherForm", createTeacherForm);
            return new ModelAndView("admin/createteacher");
        }
        model.put("createTeacherForm", new CreateTeacherForm());
        return new ModelAndView("admin/createteacher");
    }

    @RequestMapping(value = "/admin/teachers/create", method = RequestMethod.POST)
    public ModelAndView createTeacher(@Valid CreateTeacherForm createTeacherForm, Errors errors, @RequestParam(value = "id", required = false) Integer id) {
        User teacher = new User();

        teacher.setId(createTeacherForm.getId());
        teacher.setEmail(createTeacherForm.getEmail());
        teacher.setLastName(createTeacherForm.getLastName());
        teacher.setFirstName(createTeacherForm.getFirstName());
        teacher.setPhoneNumber(createTeacherForm.getPhoneNumber());
        teacher.setCountry(createTeacherForm.getCountry());
        teacher.setCity(createTeacherForm.getCity());
        teacher.setDescription(createTeacherForm.getDescription());
        teacher.setPassword(createTeacherForm.getPassword());
        teacher.setEnabled(true);

        if (id != null && id != 0) {
            usersService.update(teacher);
            return new ModelAndView(new RedirectView("/admin/teachers", true, false));
        }

        if (errors.hasErrors()) {
            return new ModelAndView("admin/createteacher");
        }

        if (usersService.exists(createTeacherForm.getEmail())) {
            errors.rejectValue("email", "DuplicateKey.user.email");
            return new ModelAndView("admin/createteacher");
        }

        usersService.save(teacher, "ROLE_TEACHER");

        return new ModelAndView(new RedirectView("/admin/teachers", true, false));
    }

    @RequestMapping(value = "/admin/teachers/{id}/delete", method = RequestMethod.GET)
    public ModelAndView createTeacher(Model model, @PathVariable("id") int id) {
        usersService.deleteUser(id);
        return new ModelAndView(new RedirectView("/admin/teachers", true, false));
    }

    @RequestMapping("teachers/{id}")
    public String viewSubject(Model model, @PathVariable(value = "id") int id) {
        User teacher = usersService.getUserById(id);
        System.out.println(teacher.getLastName());
        List<Subject> subjects = subjectService.getSubjectsByTeacherId(id);

        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);

        return "teacher";
    }

    @RequestMapping("admin/teachers/{id}/addsubject/{code}")
    public String addSubjectToTeacher(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "code") String code) {
        subjectService.addSubjectToTeacher(id, code);

        User teacher = usersService.getUserById(id);
        System.out.println(teacher.getLastName());
        List<Subject> subjects = subjectService.getSubjectsByTeacherId(id);

        model.addAttribute("teacher", teacher);
        model.addAttribute("subjects", subjects);

        return "admin/addsubject";
    }

    public static class RegisterForm {
        private int id;

        @ValidEmail
        private String email;

        @Pattern(regexp = "\\w+", message = "{Pattern.user.lastName}")
        private String lastName;

        @Pattern(regexp = "\\w+", message = "{Pattern.user.firstName}")
        private String firstName;

        private String phoneNumber;
        private String country;
        private String city;
        private String birthDate;
        private String description;

        @Pattern(regexp = "\\S+", message = "{Pattern.user.password}")
        @Size(min = 8, max = 15, message = "{Size.user.password}")
        private String password;

        private String confirmPassword;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firsName) {
            this.firstName = firsName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class CreateTeacherForm {
        private int id;

        @ValidEmail
        private String email;

        @Pattern(regexp = "\\w+", message = "{Pattern.user.lastName}")
        private String lastName;

        @Pattern(regexp = "\\w+", message = "{Pattern.user.firstName}")
        private String firstName;

        private String phoneNumber;
        private String country;
        private String city;
//        private Date birthDate;
        private String description;

        @Pattern(regexp = "\\S+", message = "{Pattern.user.password}")
        @Size(min = 8, max = 15, message = "{Size.user.password}")
        private String password;

        private String confirmPassword;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firsName) {
            this.firstName = firsName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}