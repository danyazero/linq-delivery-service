package org.zero.npservice.model.delivery.novaPost;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NPStatusRequest {
    @JsonProperty("Documents")
    private List<NPStatus> documents;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class NPStatus {
        @JsonProperty("DocumentNumber")
        private String documentNumber;
    }
}
