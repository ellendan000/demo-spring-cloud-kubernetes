package open.ending.mss.order.interfaces;


import lombok.RequiredArgsConstructor;
import open.ending.mss.order.infastructure.client.InventoryFeignClient;
import open.ending.mss.order.infastructure.client.StorageResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final InventoryFeignClient inventoryFeignClient;
    private final Random random = new Random();

    @PostMapping
    OrderResponse prepareOrder(@RequestBody PrepareOrderRequest request) {
        StorageResponse storage = inventoryFeignClient.fetchStorage(request.getProductId());
        if (storage.getEnabled()) {
            return OrderResponse.builder()
                    .productId(request.getProductId())
                    .orderId(random.nextLong())
                    .build();
        } else {
            return new OrderResponse();
        }
    }
}
