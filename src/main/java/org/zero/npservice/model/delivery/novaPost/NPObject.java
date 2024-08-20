package org.zero.npservice.model.delivery.novaPost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class NPObject<T> {
    private String apiKey;
    private NPModel modelName;
    private NPMethod calledMethod;
    private T methodProperties;
    private final String system = "DevCentre";
}
//  "apiKey": "[ВАШ КЛЮЧ]",
//   "modelName": "InternetDocumentGeneral",
//   "calledMethod": "save",
//   "methodProperties":