package com.sevendays.sevendays.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevendays.sevendays.model.Superheroi;
import com.sevendays.sevendays.repository.SuperheroiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SuperheroiRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Superheroi>> typeReference = new TypeReference<List<Superheroi>>() {};
            InputStream inputStream = new ClassPathResource("superherois.json").getInputStream();
            List<Superheroi> superherois = mapper.readValue(inputStream, typeReference);
            repository.saveAll(superherois);
        }
    }
}