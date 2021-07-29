package pl.project.goodday.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.goodday.models.DailyTask;
import pl.project.goodday.models.User;

import java.util.List;

@Repository
public interface DailyTaskRepository extends JpaRepository<DailyTask,Integer> {

    List<DailyTask> findByUser(User user);

}
