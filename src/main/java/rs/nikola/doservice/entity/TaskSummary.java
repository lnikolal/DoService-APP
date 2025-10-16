package rs.nikola.doservice.entity;



import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "dashboard_task_summary")
public class TaskSummary {

    @Id
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total")
    private Long total;

    @Column(name = "current_month")
    private Long currentMonth;



    protected TaskSummary() {}

    public String getStatus() {
        return status;
    }

    public Long getTotal() {
        return total;
    }

    public Long getCurrentMonth() {
        return currentMonth;
    }
}
