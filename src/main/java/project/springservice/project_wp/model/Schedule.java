package project.springservice.project_wp.model;

import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Schedule() {
    }

    public Schedule(LocalDateTime fromDate, LocalDateTime toDate, Tenant tenant, boolean isScheduled) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.tenant = tenant;
        this.isScheduled = isScheduled;
    }

    public String getStatus() {
        return isScheduled ? "BOOKED" : "NOT BOOKED";
    }

    public String getFromTime() {
        String [] parts = fromDate.format(dateTimeFormatter).split("\\s+");
        return String.format("Time: %s o'clock\nDate: %s", parts[1], parts[0]);
    }

    public String getToTime(){
        String [] parts = toDate.format(dateTimeFormatter).split("\\s+");
        return String.format("Time: %s o'clock\nDate: %s", parts[1], parts[0]);
    }
}
