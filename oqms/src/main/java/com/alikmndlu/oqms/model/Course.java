package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

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
    private Collection<User> students = new ArrayList<>();

    public Course(String title, LocalDate start, LocalDate end, User teacher) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
    }
}
