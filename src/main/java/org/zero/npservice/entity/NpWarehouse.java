package org.zero.npservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.zero.npservice.model.delivery.novaPost.INPWarehouse;

@Getter
@Setter
@Entity
@ToString
@Table(name = "np_warehouse")
public class NpWarehouse implements INPWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "short_address", length = Integer.MAX_VALUE)
    private String shortAddress;

    @Column(name = "city_ref", length = Integer.MAX_VALUE)
    private String cityRef;

    @Column(name = "city_description", length = Integer.MAX_VALUE)
    private String cityDescription;

    @Column(name = "hash")
    private String hash;

    @Column(name = "country_area", length = Integer.MAX_VALUE)
    private String settlementAreaDescription;

    @Column(name = "warehouse_number")
    private Integer warehouseNumber;

    @Column(name = "address_ref", length = Integer.MAX_VALUE)
    private String addressRef;

    @Column(name = "\"longitude\"")
    private Double longitude;

    @Column(name = "\"latitude\"")
    private Double latitude;

    @Column(name = "warehouse_index", length = Integer.MAX_VALUE)
    private String warehouseIndex;

    @Column(name = "\"site_key\"")
    private Integer siteKey;

}