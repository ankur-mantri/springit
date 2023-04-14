package com.vega.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Data
public class Link {
    @Id
    @GeneratedValue

    private Long id;
    private String title;
    private String url;

/*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return id.equals(link.id) && title.equals(link.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
*/
}
