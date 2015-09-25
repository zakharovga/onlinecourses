package com.onlinecourses.site;

import com.onlinecourses.site.dao.Course;
import com.onlinecourses.site.dao.MyUserDetails;
import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.User;
import com.onlinecourses.site.services.CoursesService;
import com.onlinecourses.site.services.SubjectsService;
import com.onlinecourses.site.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 03.07.2015.
 */
@Controller
public class CourseController {
    UsersService usersService;
    SubjectsService subjectsService;
    CoursesService coursesService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setSubjectService(SubjectsService subjectsService) {
        this.subjectsService = subjectsService;
    }

    @Autowired
    public void setCoursesService(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @RequestMapping("/")
    public String home(Model model) {
        List<User> users = usersService.getAllUsers();
        model.addAttribute("users", users);
        return "home";
    }

    @RequestMapping("subjects/{code}")
    public String viewSubject(Model model, @PathVariable(value = "code") String code) {
        Subject subject = subjectsService.getSubjectByCode(code);
        List<User> teachers = usersService.getTeachersBySubjectCode(code);
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);

        return "subject";
    }

    @RequestMapping("/admin/teachers")
    public String adminTeachers(Model model) {
        List<User> teachers = usersService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "admin/teachers";
    }

    @RequestMapping("/admin/subjects")
    public String adminSubjects(Model model) {
        List<Subject> subjects = subjectsService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "admin/subjects";
    }

    @RequestMapping(value = "/admin/subjects/create", method = RequestMethod.GET)
    public ModelAndView createSubject(Map<String, Object> model, @RequestParam(value = "c", required = false) String c) {
        CreateSubjectForm createSubjectForm = new CreateSubjectForm();
        if (c != null && c != "") {
            Subject subject = subjectsService.getSubjectByCode(c);

            createSubjectForm.setCode(subject.getCode());
            createSubjectForm.setName(subject.getName());
            createSubjectForm.setDescription(subject.getDescription());
            createSubjectForm.setPrice(subject.getPrice());

            model.put("c", c);
            model.put("createSubjectForm", createSubjectForm);
            return new ModelAndView("admin/createsubject");
        }
        model.put("createSubjectForm", new CreateSubjectForm());

        return new ModelAndView("admin/createsubject");
    }

    @RequestMapping(value = "/admin/subjects/create", method = RequestMethod.POST)
    public ModelAndView createSubject(@Valid CreateSubjectForm createSubjectForm, Errors errors, Model model, @RequestParam(value = "c", required = false) String c) {
        if (errors.hasErrors()) {
            model.addAttribute("c");
            return new ModelAndView("admin/createsubject");
        }

        Subject subject = new Subject();

        subject.setCode(createSubjectForm.getCode());
        subject.setName(createSubjectForm.getName());
        subject.setPrice(createSubjectForm.getPrice());
        subject.setDescription(createSubjectForm.getDescription());

        if (c != null && c != "") {
            subjectsService.save(subject, true);
            return new ModelAndView(new RedirectView("/admin/subjects", true, false));
        }
        if (subjectsService.exists(createSubjectForm.getCode())) {
            errors.rejectValue("code", "DuplicateKey.subject.code");
            return new ModelAndView("admin/createsubject");
        }

        subjectsService.save(subject, false);

        return new ModelAndView(new RedirectView("/admin/subjects", true, false));
    }

    @RequestMapping(value = "admin/subjects/{code}/addteacher")
    public String addTeacherToSubject(Model model, @PathVariable(value = "code") String code) {
        List<User> addedTeachers = subjectsService.getTeachersBySubjectCode(code);
        List<User> notAddedTeachers = subjectsService.getNotAddedTeachersBySubjectCode(code);
        model.addAttribute("addedTeachers", addedTeachers);
        model.addAttribute("notAddedTeachers", notAddedTeachers);
        model.addAttribute("code", code);

        return "admin/addteacher";
    }

    @RequestMapping(value = "admin/subjects/{code}/addteacher/{id}", method = RequestMethod.POST)
    public ModelAndView addTeacherToSubject(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "code") String code) {
        List<User> teachers = subjectsService.addTeacherToSubject(code, id);

        return new ModelAndView(new RedirectView("/admin/subjects/" + code + "/addteacher", true, false));
    }

    @RequestMapping(value = "admin/teachers/{id}/addsubject")
    public String addSubjectToTeacher(Model model, @PathVariable(value = "id") int id) {
        List<Subject> addedSubjects = subjectsService.getSubjectsByTeacherId(id);
        List<Subject> notAddedSubjects = subjectsService.getNotAddedSubjectsByTeacherId(id);
        model.addAttribute("addedSubjects", addedSubjects);
        model.addAttribute("notAddedSubjects", notAddedSubjects);
        model.addAttribute("id", id);

        return "admin/addsubject";
    }

    @RequestMapping(value = "admin/teachers/{id}/addsubject/{code}", method = RequestMethod.POST)
    public ModelAndView addSubjectToTeacher(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "code") String code) {
        List<Subject> subjects = subjectsService.addSubjectToTeacher(id, code);

        return new ModelAndView(new RedirectView("/admin/teachers/" + id + "/addsubject", true, false));
    }

    @RequestMapping(value = "/admin/subjects/{code}/delete", method = RequestMethod.GET)
    public ModelAndView deleteSubject(@PathVariable("code") String code) {
        subjectsService.deleteSubject(code);
        return new ModelAndView(new RedirectView("/admin/subjects", true, false));
    }

    @RequestMapping("/admin/courses")
    public String adminCourses(Model model) {
        List<Course> courses = coursesService.getAllCourses();
        model.addAttribute("courses", courses);
        return "admin/courses";
    }

    @RequestMapping(value = "/admin/courses/create", method = RequestMethod.GET)
    public ModelAndView createCourse(Map<String, Object> model, @RequestParam(value = "id", required = false) Integer id) {
        CreateCourseForm createCourseForm = new CreateCourseForm();

        if (id != null) {

            Course course = coursesService.getCourseById(id);


            createCourseForm.setId(course.getId());
            createCourseForm.setStartDate(course.getStartDate());
            createCourseForm.setDescription(course.getDescription());

            model.put("createCourseForm", createCourseForm);
            return new ModelAndView("admin/createcourse");
        }
        model.put("createCourseForm", new CreateCourseForm());
        return new ModelAndView("admin/createcourse");
    }

    @RequestMapping(value = "/admin/courses/create", method = RequestMethod.POST)
    public ModelAndView createCourse(@Valid CreateCourseForm createCourseForm, Errors errors, @RequestParam(value = "id", required = false) Integer id) {
        if (id != null && id != 0) {
            if (errors.hasErrors()) {
                return new ModelAndView("admin/createcourse");
            }
            Course course = new Course();
            course.setId(id);
            course.setStartDate(createCourseForm.getStartDate());
            course.setDescription(createCourseForm.getDescription());
            coursesService.update(course);
            return new ModelAndView(new RedirectView("/admin/courses", true, false));
        }
        if (errors.hasErrors()) {
            return new ModelAndView("admin/createcourse");
        }
        Course course = new Course();

        course.setId(id);
        course.setStartDate(createCourseForm.getStartDate());
        course.setDescription(createCourseForm.getDescription());

        coursesService.save(course);

        return new ModelAndView(new RedirectView("/admin/courses", true, false));
    }

    @RequestMapping(value = "admin/courses/{id}/addsubjectandteacher", method = RequestMethod.GET)
    public String addSubjectAndTeacherToCourse(Model model, @PathVariable(value = "id") int id) {
        Course course = coursesService.getCourseById(id);

        AddSubjectAndTeacherForm addSubjectAndTeacherForm = new AddSubjectAndTeacherForm();
        addSubjectAndTeacherForm.setSubjectCode(course.getSubject().getCode());
        addSubjectAndTeacherForm.setTeacherId(course.getTeacher().getId());

        List<Subject> subjects = subjectsService.getAllSubjects();
        List<User> teachers = usersService.getAllTeachers();

        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        model.addAttribute("course", course);
        model.addAttribute("addSubjectAndTeacherForm", addSubjectAndTeacherForm);

        return "admin/addsubjectandteacher";
    }

    @RequestMapping(value = "admin/courses/{id}/addsubjectandteacher", method = RequestMethod.POST)
    public ModelAndView addSubjectAndTeacherToCoursePost(AddSubjectAndTeacherForm addSubjectAndTeacherForm, @PathVariable(value = "id") int id) {
        Subject subject = subjectsService.getSubjectByCode(addSubjectAndTeacherForm.getSubjectCode());
        User teacher = usersService.getUserById(addSubjectAndTeacherForm.getTeacherId());

        coursesService.addSubjectAndTeacherToCourse(id, subject, teacher);

        return new ModelAndView(new RedirectView("/admin/courses", true, false));
    }

    @RequestMapping(value = "admin/courses/{id}/addsubject/{code}", method = RequestMethod.POST)
    public ModelAndView addSubjectToCourse(Model model, @PathVariable(value = "id") int id, @PathVariable(value = "code") String code) {
        List<Subject> subjects = subjectsService.addSubjectToTeacher(id, code);

        return new ModelAndView(new RedirectView("/admin/teachers/" + id + "/addsubject", true, false));
    }

    @RequestMapping(value = "/admin/courses/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteCourse(@PathVariable("id") int id) {
        coursesService.deleteCourse(id);
        return new ModelAndView(new RedirectView("/admin/courses", true, false));
    }

    @RequestMapping("/student")
    public String viewStudent(Model model) {
        int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<Course> courses = coursesService.getCoursesByStudentId(id);
        List<Course> newCourses = coursesService.getNewCourses(3);

        model.addAttribute("courses", courses);
        model.addAttribute("newCourses", newCourses);

        return "student";
    }

    @RequestMapping("/courses/{id}")
    public String viewCourse(Model model, @PathVariable(value = "id") int id, @RequestParam(value = "quantityerror", required = false) String quantityError) {
        int userId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Course course = coursesService.getCourseById(id);
        Boolean isEnrolled = coursesService.isEnrolled(id, userId);

        if(quantityError != null) {
            model.addAttribute("quantityError", "There are no vacancies for this course");
        }
        model.addAttribute("course", course);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("studentId", userId);

        return "course";
    }

    @RequestMapping(value = "courses/{id}/enrol/{userId}", method = RequestMethod.POST)
    public ModelAndView enrolForCourse(Model model, @PathVariable(value = "userId") int userId, @PathVariable(value = "id") int id) {
        if(!coursesService.addStudentToCourse(id, userId)) {
            return new ModelAndView(new RedirectView("/courses/" + id + "?quantityerror", true, false));
        }

        return new ModelAndView(new RedirectView("/student", true, false));
    }

    @RequestMapping("/teacher")
    public String viewTeacher(Model model) {
        int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<Course> courses = coursesService.getCoursesByTeacherId(id);


        model.addAttribute("courses", courses);

        return "teacherhome";
    }

    @RequestMapping("teacher/courses/{id}")
    public String viewCourseForTeacher(Model model, @PathVariable(value = "id") int id) {
        int teacherId = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Course course = coursesService.getCourseById(id);
        Boolean teachersCourse = course.getTeacher().getId() == teacherId;
        List<User> students = coursesService.getStudentsByCourseId(id);

        model.addAttribute("course", course);
        model.addAttribute("students", students);
        model.addAttribute("teachersCourse", teachersCourse);

        return "courseforteacher";
    }

    @RequestMapping(value = "teacher/courses/{id}/{userId}/rate", method = RequestMethod.GET)
    public ModelAndView rateStudentGet(Model model, @PathVariable(value = "userId") int userId, @PathVariable(value = "id") int id) {
        model.addAttribute("courseId", id);
        model.addAttribute("userId", userId);

        return new ModelAndView("ratestudent");
    }

    @RequestMapping(value = "teacher/courses/{id}/{userId}/rate", method = RequestMethod.POST)
    public ModelAndView rateStudent(@ModelAttribute(value = "mark") String mark, Model model, @PathVariable(value = "userId") int userId, @PathVariable(value = "id") int id) {
        coursesService.rateStudent(id, userId, mark);
        System.out.println(mark);

        return new ModelAndView(new RedirectView("/teacher/courses/" + id, true, false, false));
    }

    public static class CreateSubjectForm {
        @Pattern(regexp = "\\w+", message = "{Pattern.subject.code}")
        @Size(min = 3, max = 5, message = "{Size.subject.code}")
        private String code;

        private String name;

        private double price;
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class CreateCourseForm {
        private int id;
        private String subjectCode;
        private int teacherId;
        private String startDate;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubjectCode() {
            return subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class AddSubjectAndTeacherForm {
        private String subjectCode;
        private int teacherId;

        public String getSubjectCode() {
            return subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }
    }
}