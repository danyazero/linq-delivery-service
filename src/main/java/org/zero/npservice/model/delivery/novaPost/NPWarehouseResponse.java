package org.zero.npservice.model.delivery.novaPost;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NPWarehouseResponse extends NPResponse {
    private List<NPWarehouse> data;
}
