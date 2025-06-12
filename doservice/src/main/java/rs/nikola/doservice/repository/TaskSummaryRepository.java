package rs.nikola.doservice.repository;


import rs.nikola.doservice.entity.TaskSummary;
import org.springframework.data.repository.Repository;


import java.util.List;


public interface TaskSummaryRepository extends Repository<TaskSummary, String> {
    List<TaskSummary> findAll();
}