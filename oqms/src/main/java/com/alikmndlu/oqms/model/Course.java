package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "courses_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseModel<Long> {

    private String title;

    private LocalDate start;

    private LocalDate end;

    @ManyToOne
    private User teacher;

    @ManyToMany
    private List<User> students = new ArrayList<>();

    @OneToMany
    private List<Quiz> quizzes = new ArrayList<>();

    public Course(String title, LocalDate start, LocalDate end, User teacher) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
    }
}
