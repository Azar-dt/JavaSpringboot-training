package edu.ftv.training;

import edu.ftv.training.Model.User;
import edu.ftv.training.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaAuditing
public class TrainingApplication {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initUsers(){
        List<User> initUsers  = Stream.of(
                new User(1, "thang", "thang")
        ).collect(Collectors.toList());
        userRepository.saveAll(initUsers);
    }
    public static void main(String[] args) {
        SpringApplication.run(TrainingApplication.class, args);
    }

}
