package project.springservice.project_wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.springservice.project_wp.model.Category;
import project.springservice.project_wp.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
