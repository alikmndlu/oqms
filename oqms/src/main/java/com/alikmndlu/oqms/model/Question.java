package com.alikmndlu.oqms.model;

import com.alikmndlu.oqms.model.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseModel<Long>{

    private String title;

    private String text;

    @ManyToOne
    private User teacher;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToOne
    private Answer trueAnswer = null;

    public Question(String title, String text, User teacher) {
        this.title = title;
        this.text = text;
        this.teacher = teacher;
    }

    public QuestionType getType(){
        if (this.getTrueAnswer() == null || this.getAnswers().size() == 0){
            return QuestionType.DESCRIPTIVE;
        }
        return QuestionType.MULTI_SELECT;
    }
}
