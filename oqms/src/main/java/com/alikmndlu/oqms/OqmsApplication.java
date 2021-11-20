package com.alikmndlu.oqms;

import com.alikmndlu.oqms.model.*;
import com.alikmndlu.oqms.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class OqmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OqmsApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner runner(
            UserService userService,
            RoleService roleService,
            CourseService courseService,
            QuizService quizService,
            QuestionService questionService,
            AnswerService answerService,
            QuizQuestionService quizQuestionService) {
        return args -> {
            roleService.save(new Role("ROLE_STUDENT"));
            roleService.save(new Role("ROLE_TEACHER"));
            roleService.save(new Role("ROLE_ADMIN"));

            userService.save(new User("Ali Erfagh", "student", "password"));
            userService.save(new User("Ali Noori", "teacher", "password"));
            userService.save(new User("Ali Kmndlu", "admin", "password"));

            roleService.addRoleToUser("student", "ROLE_STUDENT");
            roleService.addRoleToUser("teacher", "ROLE_TEACHER");
            roleService.addRoleToUser("admin", "ROLE_ADMIN");

            courseService.save(new Course(
                    "Java/Spring",
                    LocalDate.parse("2022-01-10"),
                    LocalDate.parse("2022-01-30"),
                    userService.findByUsername("teacher").get()
            ));

            quizService.save(new Quiz(
                    "Spring Context Quiz",
                    "25 Question, 30 Min Time",
                    30L,
                    courseService.findById(1L).get()
            ));

            questionService.save(new Question(
                    "Title Test1",
                    "Text Test1",
                    userService.findByUsername("teacher").get()
            ));

            questionService.save(new Question(
                    "Title Test2",
                    "Text Test2",
                    userService.findByUsername("teacher").get()
            ));

            answerService.save(new Answer(
                    "Answer A1",
                    questionService.findById(1L).get()
            ));
            answerService.save(new Answer(
                    "Answer B1",
                    questionService.findById(1L).get()
            ));
            answerService.save(new Answer(
                    "Answer C1",
                    questionService.findById(1L).get()
            ));
            answerService.save(new Answer(
                    "Answer D1",
                    questionService.findById(1L).get()
            ));

            answerService.save(new Answer(
                    "Answer A2",
                    questionService.findById(2L).get()
            ));
            answerService.save(new Answer(
                    "Answer B2",
                    questionService.findById(2L).get()
            ));
            answerService.save(new Answer(
                    "Answer C2",
                    questionService.findById(2L).get()
            ));
            answerService.save(new Answer(
                    "Answer D2",
                    questionService.findById(2L).get()
            ));

            Question question = questionService.findById(1L).get();
            question.setTrueAnswer(answerService.findById(3L).get());
            questionService.save(question);

            question = questionService.findById(2L).get();
            question.setTrueAnswer(answerService.findById(5L).get());
            questionService.save(question);

            quizQuestionService.save(new QuizQuestion(
                    quizService.findById(1L).get(),
                    questionService.findById(1L).get(),
                    20L
            ));

            quizQuestionService.save(new QuizQuestion(
                    quizService.findById(1L).get(),
                    questionService.findById(2L).get(),
                    20L
            ));
        };
    }

}
