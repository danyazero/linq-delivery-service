package org.zero.npservice.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zero.npservice.entity.NpWarehouse;
import org.zero.npservice.model.Object;
import org.zero.npservice.model.ObjectDTO;
import org.zero.npservice.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface NpWarehouseRepository extends JpaRepository<NpWarehouse, Integer> {
    @Cacheable(value = "address", unless = "#result == null")
    Optional<NpWarehouse> findFirstByShortAddressLike(String shortAddress);



    @Cacheable(value = "warehouse", unless = "#result == null")
    @Query("select t from NpWarehouse t where t.cityDescription = ?1 and t.settlementAreaDescription = ?2 order by t.id asc ")
    List<NpWarehouse> findAllByCityDescriptionAndCountryArea(String city, String area);


    @Query(value = """

                select
            	W.id,
            	W.short_address as shortAddress,
            	W.warehouse_number as warehouseNumber,
            	W.city_ref as cityRef
            from np_warehouse W where W.id in (?1, ?2);
            """, nativeQuery = true)
    List<Warehouse> getWarehouseCouple(Integer senderId, Integer recipientId);

    @Query(value = """
            select distinct
                W.city_description as title,
                W.country_area as caption 
            from np_warehouse W 
            where UPPER(W.city_description) like CONCAT('%', UPPER(?1), '%') 
            LIMIT 20;
            """, nativeQuery = true)
    List<ObjectDTO> findCityByCityDescriptionLikeIgnoreCase(String city);

    @Query(value = """
select count(W.city_ref) as id, W.city_description as title, W.country_area as caption from np_warehouse W group by title, caption order by id DESC LIMIT 20;
""", nativeQuery = true)
    List<ObjectDTO> getPopularCities();


    NpWarehouse getNpWarehouseById(Integer id);
}