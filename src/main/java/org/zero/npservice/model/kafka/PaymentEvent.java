package org.zero.npservice.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.zero.npservice.model.kafka.data.Payment;

@Setter
@Getter
public class PaymentEvent extends Event {
    private Payment eventData;
}
