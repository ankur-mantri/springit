package com.vega.demo.domain;

import com.vega.demo.repository.CommentRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Link extends Auditable {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String url;

/*
    @OneToMany(mappedBy = "Link")
    private List<Comment> comments = new ArrayList<>();

    private void addComment(Comment comment) {
        comments.add(comment);
    }

 */
    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(this.url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    //this implementation needs to be corrected
    public List<String> getComments() {
        List<String> comments = new ArrayList<>();
        comments.add("Comment 1");
        comments.add("Comment 2");
        return comments;
    }
}
