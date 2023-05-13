package com.vega.demo.domain;

import com.vega.demo.service.BeanUtil;
import lombok.*;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter @Setter
@NoArgsConstructor

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

    @OneToMany(mappedBy = "link")
    private List<Vote> votes = new ArrayList<>();

    private int voteCount =0;

    @ManyToOne
    private UserSpringIt userSpringIt;
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    public String getPrettyTime() {
        PrettyTime pt = BeanUtil.getBean(PrettyTime.class);
        return pt.format(convertToDateviaInstant(getCreationDate()));
    }

    private Date convertToDateviaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(this.url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

}
