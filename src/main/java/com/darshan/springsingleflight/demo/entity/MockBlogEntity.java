package com.darshan.springsingleflight.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mockBlogData")
public class MockBlogEntity {
    @Id
    private Long id;
    private String title;
    private String body;
    private LocalDateTime date;
}
