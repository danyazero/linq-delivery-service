package org.zero.npservice.model.delivery.novaPost;

public enum NPWarehouseType {
    PARCEL_SHOP("6f8c7162-4b72-4b0a-88e5-906948c6a92f"),
    WAREHOUSE("841339c7-591a-42e2-8233-7a0a00f0ed6f"),
    CARGO("9a68df70-0267-42a8-bb5c-37f427e36ee4");

    private String ref;

    NPWarehouseType(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }
}
