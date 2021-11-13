package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
}
