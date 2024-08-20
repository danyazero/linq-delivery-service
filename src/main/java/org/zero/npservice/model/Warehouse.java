package org.zero.npservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Warehouse {
    Integer getId();
    String getShortAddress();
    Integer getWarehouseNumber();
    @JsonIgnore
    String getCityRef();
}
