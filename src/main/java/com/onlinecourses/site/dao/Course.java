package com.onlinecourses.site.dao;

/**
 * Created by zakharov_ga on 07.07.2015.
 */
public class Course {
    private int id;
    private Subject subject;
    private User teacher;
    private String startDate;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
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

    @Override
    public String toString() {
        return "Course{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
