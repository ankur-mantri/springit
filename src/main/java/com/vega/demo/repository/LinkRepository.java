package com.vega.demo.repository;

import com.vega.demo.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByTitle(String title);
}
