package org.zero.npservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@ToString
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_gen")
    @SequenceGenerator(name = "delivery_id_gen", sequenceName = "delivery_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sender_user_id", nullable = false, length = Integer.MAX_VALUE)
    private String senderUserId;

    @Column(name = "recipient_user_id", nullable = false, length = Integer.MAX_VALUE)
    private String recipientUserId;

    @Column(name = "status", nullable = false, length = 36)
    private String status;

    @Column(name = "order_id", nullable = false, length = Integer.MAX_VALUE)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "recipient_warehouse_ref", nullable = false, length = Integer.MAX_VALUE)
    private String recipientWarehouseId;

    @Column(name = "sender_warehouse_ref", nullable = false, length = Integer.MAX_VALUE)
    private String senderWarehouseId;

    @Column(name = "recipient_city_ref", nullable = false, length = Integer.MAX_VALUE)
    private String recipientCityId;

    @Column(name = "sender_city_ref", nullable = false, length = Integer.MAX_VALUE)
    private String senderCityId;

    @Column(name = "recipient_warehouse_number", nullable = false)
    private Integer recipientWarehouseNumber;

    @Column(name = "sender_warehouse_number", nullable = false)
    private Integer senderWarehouseNumber;

    @Column(name = "cart_price", nullable = false)
    private Double cartPrice;

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parcel_type_id")
    private Parcel parcelTypeId;

}