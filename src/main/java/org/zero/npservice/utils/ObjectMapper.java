package org.zero.npservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zero.npservice.model.Object;
import org.zero.npservice.model.ObjectDTO;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ObjectMapper implements Function<ObjectDTO, Object> {
    private final UUIDWarehouseProvider uuidProvider;

    @Override
    public Object apply(ObjectDTO element) {
        return new Object(
                uuidProvider.generate(element.getCaption(), element.getTitle()),
                element.getTitle(),
                element.getCaption()
        );
    }
}
