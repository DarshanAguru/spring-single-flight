package com.darshan.springsingleflight.demo.repos;

import com.darshan.springsingleflight.demo.entity.MockBlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockBlogRepository extends JpaRepository<MockBlogEntity, Long> {

}
