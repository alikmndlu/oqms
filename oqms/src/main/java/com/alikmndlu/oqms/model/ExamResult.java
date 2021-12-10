package com.alikmndlu.oqms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_result_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult extends BaseModel<Long> {

    @ManyToOne
    private User student;

    @ManyToOne
    private Quiz quiz;

    private Boolean isFinished;

    private LocalDateTime examStartTime;

    private LocalDateTime examEndTime;
}
