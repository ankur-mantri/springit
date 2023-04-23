package com.vega.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
public class Comment extends Auditable {
    @Id
    @GeneratedValue
    private Long id;
    private String body;

    //link
    @ManyToOne
    private Link link;

}
