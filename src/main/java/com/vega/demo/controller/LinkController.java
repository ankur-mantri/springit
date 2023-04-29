package com.vega.demo.controller;

import com.vega.demo.domain.Link;
import com.vega.demo.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {
    private LinkRepository linkRepository;
    @Autowired
    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }
    @GetMapping("/")
    public List<Link> list() {
        return linkRepository.findAll();
    }
    //CRUD
    @PostMapping("/create")
    public Link create(@ModelAttribute Link link) {
        return linkRepository.save(link);
    }

    @GetMapping("/{id}")
    public Optional<Link> read(@PathVariable Long id) {
        return linkRepository.findById(id);
    }
    @PutMapping("/{id}")
    public Link update(@ModelAttribute Link link) {
        return linkRepository.save(link);
    }
    @DeleteMapping("/delete/{id}")
    public Link delete(@PathVariable Long id) {
        linkRepository.deleteById(id);
        return  null;
    }

}
