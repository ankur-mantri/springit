package com.vega.demo.controller;

import com.vega.demo.domain.Comment;
import com.vega.demo.domain.Link;
import com.vega.demo.service.CommentService;
import com.vega.demo.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LinkController {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    private LinkService linkService;
    private CommentService commentService;
    @Autowired
    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }


    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkService.findAll());
        model.addAttribute("pageTitle", "LIST OF LINKS");
        return "link/list";
    }
    @GetMapping("/u/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        return "Test";
    }
    @GetMapping("link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> link = linkService.findById(id);
        if (link.isPresent()) {
            Link currentLink = link.get();
            Comment comment = new Comment();
            comment.setLink(currentLink);
            model.addAttribute("comment", comment);
            model.addAttribute("link", currentLink);
            model.addAttribute("success",model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/submit")
    public String createLink(Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            logger.info("Validation errors were found while submitting a new link.");
            model.addAttribute("link",link);
            return "link/submit";
        } else {
            // save our link
            linkService.save(link);
            logger.info("New link was saved successfully");
            redirectAttributes
                    .addAttribute("id",link.getId())
                    .addFlashAttribute("success",true);
            return "redirect:/link/{id}";
        }
    }
    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String createComment(Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("Unable to Add comments");

        } else  {
            commentService.save(comment);
            logger.info("Comment Saved");

        }
        return "redirect:/link/" + comment.getLink().getId();
    }
}
