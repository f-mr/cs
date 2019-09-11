package com.cs;

import com.cs.service.LogFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class CsApplication implements CommandLineRunner {

    @Autowired
    private LogFileReader reader;


    public static void main(String[] args) {
        Stream.of(args)
                .forEach(System.out::println);

        SpringApplication.run(CsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Stream.of(args)
                .forEach(path -> reader.readFile(path));

    }

}
