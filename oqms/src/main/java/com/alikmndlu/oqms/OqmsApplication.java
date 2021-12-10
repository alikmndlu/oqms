package com.alikmndlu.oqms;

import com.alikmndlu.oqms.model.*;
import com.alikmndlu.oqms.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

            userService.insertNewUser(new User("Ali Erfagh", "student", "password", true));
            userService.insertNewUser(new User("Ali Noori", "teacher", "password", true));
            userService.insertNewUser(new User("Ali Kmndlu", "admin", "password", true));
            userService.insertNewUser(new User("Ali Najafi", "najafi", "password"));
            userService.insertNewUser(new User("Amin Alvandi", "alvandi", "password"));

            roleService.addRoleToUser("student", "ROLE_STUDENT");
            roleService.addRoleToUser("teacher", "ROLE_TEACHER");
            roleService.addRoleToUser("admin", "ROLE_ADMIN");
            roleService.addRoleToUser("najafi", "ROLE_TEACHER");
            roleService.addRoleToUser("alvandi", "ROLE_TEACHER");

            courseService.save(new Course(
                    "Java/Spring",
                    LocalDate.parse("2022-01-10"),
                    LocalDate.parse("2022-01-30"),
                    userService.findByUsername("teacher").get()
            ));

            courseService.addStudentToCourse(
                    1L,
                    "student"
            );

            courseService.save(new Course(
                    "ES6",
                    LocalDate.parse("2022-02-05"),
                    LocalDate.parse("2022-02-15"),
                    userService.findByUsername("teacher").get()
            ));

            quizService.save(new Quiz(
                    "Math Exam",
                    "3 Question, 3 Min Time",
                    3L,
                    courseService.findById(1L).get()
            ));

            questionService.save(new Question(
                    "zarb3*4",
                    "What is Output Of 3 * 4 ?",
                    userService.findByUsername("teacher").get()
            ));

            questionService.save(new Question(
                    "zarb4*4",
                    "What is Output Of 4 * 4 ?",
                    userService.findByUsername("teacher").get()
            ));

            questionService.save(new Question(
                    "describeQuestion",
                    "Describe Yourself in 3 Sentence?",
                    userService.findByUsername("teacher").get()
            ));

            answerService.save(new Answer(
                    "Answer 10",
                    questionService.findById(1L).get()
            ));
            answerService.save(new Answer(
                    "Answer 12",
                    questionService.findById(1L).get()
            ));
            answerService.save(new Answer(
                    "Answer 8",
                    questionService.findById(1L).get()
            ));
            answerService.save(new Answer(
                    "Answer 16",
                    questionService.findById(1L).get()
            ));

            answerService.save(new Answer(
                    "Answer 14",
                    questionService.findById(2L).get()
            ));
            answerService.save(new Answer(
                    "Answer 18",
                    questionService.findById(2L).get()
            ));
            answerService.save(new Answer(
                    "Answer 16",
                    questionService.findById(2L).get()
            ));
            answerService.save(new Answer(
                    "Answer 20",
                    questionService.findById(2L).get()
            ));

            Question question = questionService.findById(1L).get();
            question.setTrueAnswer(answerService.findById(2L).get());
            questionService.save(question);

            question = questionService.findById(2L).get();
            question.setTrueAnswer(answerService.findById(7L).get());
            questionService.save(question);

            quizQuestionService.save(new QuizQuestion(
                    quizService.findById(1L).get(),
                    questionService.findById(1L).get(),
                    30L
            ));

            quizQuestionService.save(new QuizQuestion(
                    quizService.findById(1L).get(),
                    questionService.findById(2L).get(),
                    30L
            ));

            quizQuestionService.save(new QuizQuestion(
                    quizService.findById(1L).get(),
                    questionService.findById(3L).get(),
                    40L
            ));

            Quiz quiz = quizService.findById(1L).get();
            quiz.setIsComplete(true);
            quizService.save(quiz);
        };
    }

}
