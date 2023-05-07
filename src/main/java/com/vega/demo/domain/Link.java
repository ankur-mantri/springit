package com.vega.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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


    @OneToMany(mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<String> getComments(){
        List<String> randomComments = new ArrayList<>();
        randomComments.add("This is funny");
        randomComments.add("This is awesome");
        randomComments.add("This is random");
        randomComments.add("Dont visit this ever");
        randomComments.add("This is super site");
        return randomComments;
    }
    public String getPrettyTime(){
        return this.getCreationDate().toString();
    }
    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(this.url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

}
