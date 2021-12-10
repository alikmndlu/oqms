package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exams_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exam extends BaseModel<Long>{

    @ManyToOne
    private User student;

    @ManyToOne
    private QuizQuestion quizQuestion;

    private String answer;
}
