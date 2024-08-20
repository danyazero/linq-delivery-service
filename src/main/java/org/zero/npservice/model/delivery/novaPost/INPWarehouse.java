package org.zero.npservice.model.delivery.novaPost;

public interface INPWarehouse {
    String getWarehouseIndex();
    String getShortAddress();
    String getCityRef();
    String getCityDescription();
    String getSettlementAreaDescription();
    Integer getWarehouseNumber();
    String getAddressRef();
//    String getSettlementRef();
    Double getLongitude();
    Double getLatitude();
    Integer getSiteKey();
}
