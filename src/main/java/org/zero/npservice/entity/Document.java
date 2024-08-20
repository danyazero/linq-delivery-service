package org.zero.npservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document")
public class Document {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "document_ref", nullable = false, length = Integer.MAX_VALUE)
    private String documentRef;

    @Column(name = "document_number", nullable = false, length = Integer.MAX_VALUE)
    private String documentNumber;

}