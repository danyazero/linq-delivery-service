package org.zero.npservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zero.npservice.entity.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
}