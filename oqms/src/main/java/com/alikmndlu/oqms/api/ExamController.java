package com.alikmndlu.oqms.api;

import com.alikmndlu.oqms.dto.ExamAnswerIdTypeScoreQuestionAnswer;
import com.alikmndlu.oqms.dto.ExamResultIdStudentIdStudentNameDate;
import com.alikmndlu.oqms.model.*;
import com.alikmndlu.oqms.model.enums.QuestionType;
import com.alikmndlu.oqms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExamController {

    private final ExamAnswerService examAnswerService;

    private final UserService userService;

    private final QuizService quizService;

    private final QuizQuestionService quizQuestionService;

    private final ExamResultService examResultService;


    @GetMapping("/test/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    private ResponseEntity<?> test(
            @PathVariable("quiz-id") Long quizId) {

        Quiz quiz = quizService.findById(quizId).get();
        return ResponseEntity.ok().body(quiz.getInfo());
    }

    @GetMapping("/student/exam/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getQuestionsOfExam(@PathVariable("quiz-id") Long quizId) {
        Quiz quiz = quizService.findById(quizId).get();
        List<QuizQuestion> quizQuestions = quizQuestionService.findByQuiz(quiz);
        List<Map<String, Object>> result = new ArrayList<>();

        int index = 1;
        for (QuizQuestion quizQuestion : quizQuestions) {
            Question question = quizQuestion.getQuestion();
            Map<String, Object> map = new HashMap<>();
            map.put("id", index);
            map.put("quizQuestionId", quizQuestion.getId());
            map.put("score", quizQuestion.getScore());
            map.put("text", question.getText());
            map.put("type", question.getType().name());
            if (question.getType().equals(QuestionType.MULTI_SELECT)) {
                map.put("answers", question.getAnswers().stream()
                        .map(Answer::getText)
                        .collect(Collectors.toList()));
            }
            result.add(map);
            index++;
        }
        return ResponseEntity.ok().body(result);
    }


    // insert exam answers
    @PostMapping("/student/exam/insert-answers/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public void insertExamAnswers(@RequestBody Map<Long, String> dto, @PathVariable("quiz-id") Long quizId) {
        User student = userService.getLoggedInUser();
        for (Map.Entry<Long, String> entry : dto.entrySet()) {
            Long quizQuestionId = entry.getKey();
            String answer = entry.getValue();
            QuizQuestion quizQuestion = quizQuestionService.findById(quizQuestionId).get();

            Long score = null;
            if (quizQuestion.getQuestion().getType() == QuestionType.MULTI_SELECT) {
                if (quizQuestion.getQuestion().getTrueAnswer().getText().equals(answer)) {
                    score = quizQuestion.getScore();
                } else {
                    score = 0L;
                }
            }

            examAnswerService.save(new ExamAnswer(
                    student,
                    quizQuestion,
                    answer,
                    score
            ));
        }

        ExamResult examResult = examResultService.findByQuizIdAndStudentId(quizId, student.getId()).get();
        examResult.setIsFinished(true);
        examResult.setExamEndTime(LocalDateTime.now());
        examResultService.save(examResult);
    }

    // fetch student answers of the quiz
    @GetMapping("/exam/{quiz-id}/answers/{student-id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<List<ExamAnswerIdTypeScoreQuestionAnswer>> getStudentAnswersToQuiz(
            @PathVariable("quiz-id") Long quizId,
            @PathVariable("student-id") Long studentId) {

        List<ExamAnswer> examAnswers = examAnswerService.findByQuizIdAndStudentId(quizId, studentId);

        return ResponseEntity.ok().body(
                examAnswers.stream()
                        .map(examAnswer ->
                                new ExamAnswerIdTypeScoreQuestionAnswer(
                                        examAnswer.getId(),
                                        examAnswer.getQuizQuestion().getQuestion().getType(),
                                        examAnswer.getScore(),
                                        examAnswer.getQuizQuestion().getQuestion().getText(),
                                        examAnswer.getAnswer()
                                )).collect(Collectors.toList())
        );
    }

    // get students list that answers to quiz
    @GetMapping("/exam/{quiz-id}/students")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getStudentListThatAnswerToQuiz(@PathVariable("quiz-id") Long quizId) {
        List<ExamResultIdStudentIdStudentNameDate> result = new ArrayList<>();
        List<ExamResult> examResultList = examResultService.findStudentsThatAnswerToQuiz(quizId);
        examResultList.forEach(examResult -> {
            ExamResultIdStudentIdStudentNameDate dto = new ExamResultIdStudentIdStudentNameDate(
                    examResult.getId(),
                    examResult.getStudent().getId(),
                    examResult.getStudent().getName(),
                    examResult.getExamEndTime().toLocalDate()
            );
            result.add(dto);
        });
        return ResponseEntity.ok().body(result);
    }

    // update exam answer score
    @PutMapping("/teacher/exam/answer/{exam-answer-id}/update-score/{new-score}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void updateExamAnswerScore(@PathVariable("exam-answer-id") Long examAnswerId, @PathVariable("new-score") Long newScore) {
        ExamAnswer examAnswer = examAnswerService.findById(examAnswerId).get();
        examAnswer.setScore(newScore);
        examAnswerService.save(examAnswer);
    }

    // get question score
    @GetMapping("/question/{exam-answer-id}/score")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<Long> getQuestionScore(@PathVariable("exam-answer-id") Long examAnswerId) {
        ExamAnswer examAnswer = examAnswerService.findById(examAnswerId).get();
        return ResponseEntity.ok().body(examAnswer.getQuizQuestion().getScore());
    }

    // calculate and get student exam score
    @GetMapping("student/exam/final-result/{quiz-id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getStudentFinalScore(@PathVariable("quiz-id") Long quizId) {
        User student = userService.getLoggedInUser();
        Quiz quiz = quizService.findById(quizId).get();
        Map<String, Long> result = new HashMap<>();

        List<QuizQuestion> quizQuestions = quizQuestionService.findByQuiz(quiz);
        long totalExamScore = 0L;
        for (QuizQuestion quizQuestion : quizQuestions) {
            totalExamScore += quizQuestion.getScore();
        }
        result.put("totalExamScore", totalExamScore);

        List<ExamAnswer> examAnswers = examAnswerService.findByQuizIdAndStudentId(quizId, student.getId());
        long studentScore = 0L;
        for (ExamAnswer examAnswer : examAnswers) {
            if (examAnswer.getScore() != null) {
                studentScore += examAnswer.getScore();
            }
        }
        result.put("studentScore", studentScore);
        return ResponseEntity.ok().body(
                result
        );
    }
}
