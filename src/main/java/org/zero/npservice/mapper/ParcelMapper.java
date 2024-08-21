package org.zero.npservice.mapper;

import org.zero.npservice.entity.Delivery;
import org.zero.npservice.model.delivery.Parcel;
import org.zero.npservice.model.delivery.novaPost.NPSeat;

public class ParcelMapper {

    public static Parcel map(org.zero.npservice.entity.Parcel parcelDetails, Delivery delivery) {
        return ParcelMapper.map(parcelDetails, delivery.getDescription(), delivery.getCartPrice());
    }

    public static Parcel map(
            org.zero.npservice.entity.Parcel parcelDetails, String description, Double cartPrice) {
        return new Parcel(
                description,
                parcelDetails.getWeight(),
                parcelDetails.getLength(),
                parcelDetails.getWidth(),
                parcelDetails.getHeight(),
                cartPrice);
    }

    public static NPSeat map(Parcel parcelDetails) {
        return new NPSeat(
                String.valueOf(parcelDetails.getWidth()),
                String.valueOf(parcelDetails.getLength()),
                String.valueOf(parcelDetails.getHeight()),
                String.valueOf(parcelDetails.getWeight()));
    }
}
