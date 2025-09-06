package mayton.chess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class QueenSolver {

    static Logger logger = LoggerFactory.getLogger(QueenSolver.class);

    public static void main(String[] args) {

        SpringApplication.run(QueenSolver.class, args);

    }
}
