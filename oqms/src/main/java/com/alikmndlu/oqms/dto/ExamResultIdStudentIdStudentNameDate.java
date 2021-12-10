package com.alikmndlu.oqms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultIdStudentIdStudentNameDate {
    private Long id;

    private Long studentId;

    private String name;

    private LocalDate date;
}
