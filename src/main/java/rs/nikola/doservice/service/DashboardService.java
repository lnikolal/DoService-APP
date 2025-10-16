package rs.nikola.doservice.service;


import org.springframework.stereotype.Service;
import rs.nikola.doservice.repository.TaskSummaryRepository;
import rs.nikola.doservice.entity.TaskSummary;

import java.util.List;

@Service
public class DashboardService {

    private final TaskSummaryRepository taskSummaryRepository;


    public DashboardService(TaskSummaryRepository taskSummaryRepository) {
        this.taskSummaryRepository = taskSummaryRepository;
    }

    public List<TaskSummary> getDashboardSummary() {

        return taskSummaryRepository.findAll();
    }
}

