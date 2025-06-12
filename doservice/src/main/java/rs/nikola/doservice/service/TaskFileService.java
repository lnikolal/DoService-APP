package rs.nikola.doservice.service;


import org.springframework.stereotype.Service;
import rs.nikola.doservice.entity.Task;
import rs.nikola.doservice.entity.TaskFile;
import rs.nikola.doservice.entity.TaskStatus;
import rs.nikola.doservice.repository.TaskFileRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskFileService {

    private TaskFileRepository taskFileRepository;
    public TaskFileService(TaskFileRepository taskFileRepository) {
        this.taskFileRepository = taskFileRepository;

    }

    public List<TaskFile> getAllTaskFiles() {
        return taskFileRepository.findByDeletedAtIsNull();
    }

    public TaskFile getTaskFileById(Long id) {
        return taskFileRepository.findByIdAndDeletedAtIsNull(id) .orElseThrow(() -> new NoSuchElementException("No such file"));
    }

    public List<TaskFile> getTaksFileByTask(Task task) {
        return taskFileRepository.findByTaskAndDeletedAtIsNull(task);
    }



}
