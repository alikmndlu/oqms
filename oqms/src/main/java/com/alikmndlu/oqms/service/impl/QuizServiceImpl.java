package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.dto.QuizTitleInfoTimeCourseIdDto;
import com.alikmndlu.oqms.model.Quiz;
import com.alikmndlu.oqms.repository.QuizRepository;
import com.alikmndlu.oqms.service.CourseService;
import com.alikmndlu.oqms.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class QuizServiceImpl extends BaseServiceImpl<Quiz, Long, QuizRepository>
        implements QuizService {

    private final QuizRepository quizRepository;

    private final CourseService courseService;


    public QuizServiceImpl(QuizRepository quizRepository, CourseService courseService) {
        super(quizRepository);
        this.quizRepository = quizRepository;
        this.courseService = courseService;
    }

    @Override
    public void addQuizForCourse(QuizTitleInfoTimeCourseIdDto quizDto) {
        System.out.println(quizDto);
        quizRepository.save(new Quiz(
                quizDto.getTitle(),
                quizDto.getInfo(),
                quizDto.getTime(),
                courseService.findById(quizDto.getCourseId()).get()
        ));
    }
}