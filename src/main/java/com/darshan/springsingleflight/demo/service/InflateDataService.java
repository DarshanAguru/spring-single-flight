package com.darshan.springsingleflight.demo.service;

import com.darshan.springsingleflight.demo.entity.MockBlogEntity;
import com.darshan.springsingleflight.demo.repos.MockBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InflateDataService {

    @Autowired
    private MockBlogRepository repository;

    public void inflateData()
    {
        System.out.println("Inflating data...");
        List<MockBlogEntity> data = List.of(
                add(1232L, "Blog 1232", "This is demo blog with ID 1232", LocalDateTime.of(2026, 2, 26, 10, 10)),
                add(15L, "Blog 15", "This is demo blog with ID 15", LocalDateTime.of(2025, 11, 12, 16, 12)),
                add(2043234241L, "Blog 20432324241", "This is demo blof with ID 20432324241", LocalDateTime.of(2024, 12, 12, 19, 10))
        );

        repository.saveAll(data);
        System.out.println("Data Inflated");
    }

    private MockBlogEntity add(Long id, String title, String body, LocalDateTime dateTime)
    {
        return new MockBlogEntity(id, title, body, dateTime);
    }


}
