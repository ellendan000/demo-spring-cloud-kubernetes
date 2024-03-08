package open.ending.mss.order.infastructure.client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "inventory-service")
@CircuitBreaker(name="storage", fallbackMethod = "fallback")
@Retry(name="storage")
public interface InventoryFeignClient {

    @GetMapping("/storages/{productId}")
    StorageResponse fetchStorage(@PathVariable("productId") String productId);

    default StorageResponse fallback(String productId, CallNotPermittedException e) {
        return StorageResponse.builder()
                .id(productId)
                .enabled(false)
                .build();
    }
 }
