package org.zero.npservice.model.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class Parcel {
//        private String deliveryProvider;
        private String description;
        private Double weight;
        private Double length;
        private Double width;
        private Double height;
        private Double cost;
}
