package org.zero.npservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserData(String firstName, String lastName, String middleName, String phone, String userId) {
}