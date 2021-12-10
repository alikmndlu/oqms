package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.Exam;
import com.alikmndlu.oqms.repository.ExamRepository;
import com.alikmndlu.oqms.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class ExamServiceImpl extends BaseServiceImpl<Exam, Long, ExamRepository>
        implements ExamService {

    private final ExamRepository repository;

    public ExamServiceImpl(ExamRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
