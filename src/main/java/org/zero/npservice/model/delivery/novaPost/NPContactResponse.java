package org.zero.npservice.model.delivery.novaPost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NPContactResponse extends NPResponse {
    private List<NPContactPerson> data;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NPContactPerson {
        @JsonProperty("FirstName")
        private String firstName;
        @JsonProperty("LastName")
        private String lastName;
        @JsonProperty("MiddleName")
        private String middleName;
        @JsonProperty("Description")
        private String description;
        @JsonProperty("Phones")
        private String phone;
        @JsonProperty("Ref")
        private String ref;
    }
}
