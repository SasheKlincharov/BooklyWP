package project.springservice.project_wp.model;

import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private LocalDateTime fromDate;
    @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy")
    private LocalDateTime toDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Tenant tenant;
    private boolean isScheduled;

    public Schedule() {
    }
}
