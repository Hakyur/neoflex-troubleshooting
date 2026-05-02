package ru.rogotovsky.troubleshooting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovsky.troubleshooting.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
