package com.ratatouille.Ratatouille23.table;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
@Slf4j
public class TableConfig {

    @Bean
    CommandLineRunner tableRunner(TableRepository dao) {
        return args -> {
            Table a = new Table(
                    12,
                    5
            );

            Table b = new Table(
                    2,
                    2
            );

            dao.saveAll(List.of(a, b));
        };
    }
}
