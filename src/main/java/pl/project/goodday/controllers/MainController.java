package pl.project.goodday.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.project.goodday.dtos.DailyTaskDto;
import pl.project.goodday.dtos.UserDto;
import pl.project.goodday.exceptions.UserAlreadyExistException;
import pl.project.goodday.models.DailyTask;
import pl.project.goodday.models.GoldenThought;
import pl.project.goodday.models.User;
import pl.project.goodday.repositories.GoldenThoughtsRepository;
import pl.project.goodday.services.DailyTaskService;
import pl.project.goodday.services.MyUserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/")
public class MainController {

    private final MyUserDetailsService myUserDetailsService;
    private final DailyTaskService dailyTaskService;
    private final GoldenThoughtsRepository goldenThoughtsRepository;
//    @Value("${hearth.beat}")
    private String hearthBeatMsg = "hello";

    @Autowired
    public MainController(MyUserDetailsService myUserDetailsService, DailyTaskService dailyTaskService, GoldenThoughtsRepository goldenThoughtsRepository) {
        this.myUserDetailsService = myUserDetailsService;
        this.dailyTaskService = dailyTaskService;
        this.goldenThoughtsRepository = goldenThoughtsRepository;
    }

    @GetMapping("user")
    public String user() {
        return ("<h1>Welcome User</h1>");
    }

    @GetMapping("admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }

    @PostMapping("register")
    public String registerUserAccount(@RequestBody UserDto userDto) {
        try {
            User registered = myUserDetailsService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException | IllegalStateException exception) {
            return exception.getMessage();
        }
        return "Użytkownik został zarejestrowany";
    }

    @GetMapping("goldenThought")
    public GoldenThought getRandomGoldenThought() {
        List<Integer> sententencesIndexes = goldenThoughtsRepository.getAllIndexes();
        Random random = new Random();
        int sentenceNumber = random.nextInt(sententencesIndexes.size());
        Optional<GoldenThought> goldenThoughtsOptional = goldenThoughtsRepository.findById(sententencesIndexes.get(sentenceNumber));
        return goldenThoughtsOptional.orElse(new GoldenThought("Me", "Idź do pracy, nie słuchaj kołczów"));
    }


    @PostMapping("addTask")
    public ResponseEntity<String>  addTask(@RequestBody DailyTaskDto dailyTaskDto) {
        int taskId = dailyTaskService.saveDailyTask(dailyTaskDto);
        return new ResponseEntity<>(String.valueOf(taskId), HttpStatus.OK);
    }
    @DeleteMapping("deleteTask/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        dailyTaskService.deleteTask(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("findAllTasksForUser")
    public List<DailyTask> findAllTasksForUser() {
        return dailyTaskService.findAllTasksForUser();
    }


    @GetMapping("getTask/{id}")
    public DailyTask getTask(@PathVariable int id) {
        try {
            return dailyTaskService.findTaskById(id);
        } catch (Throwable throwable) {
            return new DailyTask();
        }
    }

    @GetMapping("ping")
    public String hearthBeat() {
        return hearthBeatMsg;
    }

    @GetMapping("login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("Logged successfull", HttpStatus.OK);
    }

}

