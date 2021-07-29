package pl.project.goodday.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.project.goodday.DbInitializer;
import pl.project.goodday.repositories.DailyTaskRepository;
import pl.project.goodday.repositories.UserRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DailyTaskTest {

    private UserRepository userRepository;
    private DailyTaskRepository dailyTaskRepository;
    private DbInitializer dbInitializer;

    @Autowired
    public DailyTaskTest(UserRepository userRepository, DailyTaskRepository dailyTaskRepository, DbInitializer dbInitializer) {
        this.userRepository = userRepository;
        this.dailyTaskRepository = dailyTaskRepository;
        this.dbInitializer = dbInitializer;
    }

    @Transactional
    @Test
    public void addDailyTasks(){
        dbInitializer.createAdmin();
        Optional<User> admin = userRepository.findByUserName("admin");


        DailyTask dailyTask1 = new DailyTask();
        dailyTask1.setTask("Pozmywać okna");
        dailyTask1.setSuccess("Pozmywałem podłoge");
        dailyTask1.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        dailyTask1.setUser(admin.orElse(null));

        DailyTask dailyTask2 = new DailyTask();
        dailyTask2.setTask("Poczytać książke");
        dailyTask2.setSuccess("Napisałem test jednostkowy");
        dailyTask2.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        dailyTask2.setUser(admin.get());

        admin.orElse(null).setDailyTasks(Arrays.asList(dailyTask1,dailyTask2));

        Optional<User> admin1 = userRepository.findByUserName("admin");
        assertEquals(2,admin1.orElse(null).getDailyTasks().stream().count());
    }
}