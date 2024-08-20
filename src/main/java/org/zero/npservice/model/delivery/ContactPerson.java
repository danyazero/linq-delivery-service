package org.zero.npservice.model.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ContactPerson {
    @JsonProperty("CounterpartyRef")
    private String counterpartyRef;
    @JsonProperty("FirstName")
    private final String firstName;
    @JsonProperty("LastName")
    private final String lastName;
    @JsonProperty("MiddleName")
    private final String middleName;
    @JsonProperty("Phone")
    private final String phone;
    @JsonIgnore
    private final boolean isSender;
}
