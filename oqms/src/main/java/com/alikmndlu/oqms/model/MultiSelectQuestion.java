package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "multi_select_questions_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultiSelectQuestion extends BaseModel<Long>{

    private String title;

    private String text;

    @ManyToOne
    private User teacher;

//    @OneToMany
//    private List<Answer> answers = new ArrayList<>();

//    @OneToOne
//    private Answer trueAnswer = null;

//    public MultiSelectQuestion(String title, String text, User teacher) {
//        this.title = title;
//        this.text = text;
//        this.teacher = teacher;
//    }
}
