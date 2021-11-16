package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answers_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseModel<Long> {

    private String text;

    @ManyToOne
    private MultiSelectQuestion question;
}
