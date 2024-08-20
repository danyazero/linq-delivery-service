package org.zero.npservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parcel")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

}