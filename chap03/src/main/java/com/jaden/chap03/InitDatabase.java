package com.jaden.chap03;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class InitDatabase {
    @Bean
    public CommandLineRunner init(ImageRepository imageRepository) {
        Object[][] data = {
                {"1", "learning-spring-boot-cover.jpg"},
                {"2", "learning-spring-boot-2nd-edition-cover.jpg"},
                {"3", "bazinga.png"},
        };
        return args -> {
            imageRepository
                    .deleteAll()
                    .thenMany(
                            Flux.just(data)
                            .map(arr -> new Image((String)arr[0], (String)arr[1]))
                            .flatMap(imageRepository::save)
                    )
                    .thenMany(imageRepository.findAll())
                    .subscribe(image -> System.out.println("saving "+image));
        };
    }
}
