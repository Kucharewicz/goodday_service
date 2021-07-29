package pl.project.goodday.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.goodday.dtos.DailyTaskDto;
import pl.project.goodday.models.DailyTask;
import pl.project.goodday.models.User;
import pl.project.goodday.repositories.DailyTaskRepository;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class DailyTaskService {

    private final DailyTaskRepository dailyTaskRepository;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public DailyTaskService(DailyTaskRepository dailyTaskRepository, MyUserDetailsService myUserDetailsService) {
        this.dailyTaskRepository = dailyTaskRepository;
        this.myUserDetailsService = myUserDetailsService;
    }

    public List<DailyTask> findAllTasksForUser() {
        User loggedUserFromDb = myUserDetailsService.getLoggedUserFromDb();
        return findTasksForLoggedUser(loggedUserFromDb);
    }


    public int saveDailyTask(DailyTaskDto dailyTaskDto) {
        User loggedUserFromDb = myUserDetailsService.getLoggedUserFromDb();
        List<DailyTask> tasksForLoggedUser = findTasksForLoggedUser(loggedUserFromDb);
        int updatedIndex = updateTaskIfExists(dailyTaskDto);
        if (updatedIndex>-1){
            return updatedIndex;
        }
        DailyTask dailyTask = new DailyTask();
        dailyTask.setTask(dailyTaskDto.getTask());
        dailyTask.setUser(loggedUserFromDb);
        dailyTask.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        dailyTask.setSuccess(dailyTaskDto.getSuccess());
        tasksForLoggedUser.add(dailyTask);
        return dailyTaskRepository.save(dailyTask).getId();
    }

    public int updateTaskIfExists(DailyTaskDto dailyTaskDto) {
        try {
            DailyTask task = findTaskById(dailyTaskDto.getId());
            task.setTask(dailyTaskDto.getTask());
            task.setSuccess(dailyTaskDto.getSuccess());
            return dailyTaskRepository.save(task).getId();
        } catch (Throwable throwable) {
            return -1;
        }
    }



    private List<DailyTask> findTasksForLoggedUser(User loggedUserFromDb){
        return dailyTaskRepository.findByUser(loggedUserFromDb);
    }

    public void deleteTask(int id) {
        dailyTaskRepository.deleteById(id);
    }

    public DailyTask findTaskById(int id) throws Throwable {
        Optional<DailyTask> optionalTask = dailyTaskRepository.findById(id);
        return optionalTask.orElseThrow((Supplier<Throwable>) () -> new RuntimeException("Task not found"));
    }
}
