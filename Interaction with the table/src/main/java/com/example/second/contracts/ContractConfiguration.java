package com.example.second.contracts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ContractConfiguration {

    @Bean
    CommandLineRunner commandLineRunnerHero(ContractRepository contractRepository) {
        return args -> {
            contractRepository.saveAll(List.of(
                    new Contract(LocalDate.of(2020, 1, 27), 1011, LocalDate.of(2020, 4, 27)),
                    new Contract(LocalDate.of(2019, 1, 27), 1012, LocalDate.of(2019, 7, 27)),
                    new Contract(LocalDate.of(2018, 1, 27), 1013, LocalDate.of(2018, 2, 27)),
                    new Contract(LocalDate.of(2017, 1, 27), 1014, LocalDate.of(2017, 1, 27)))
            );
        };
    }
}
