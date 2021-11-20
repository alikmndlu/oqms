package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "quizes_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz extends BaseModel<Long> {

    private String title;

    private String info;

    private Long time;

    @ManyToOne
    private Course course;

//    public Quiz(String title, String info, Long time, Course course) {
//        this.title = title;
//        this.info = info;
//        this.time = time;
//        this.course = course;
//    }
}
