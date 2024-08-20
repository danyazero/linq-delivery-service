package org.zero.npservice.model;

public record Delivery(String sender, String recipient, Double price, Integer parcelType){}