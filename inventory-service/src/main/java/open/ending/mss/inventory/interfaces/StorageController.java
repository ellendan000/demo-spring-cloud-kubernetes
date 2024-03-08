package open.ending.mss.inventory.interfaces;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/storages")
public class StorageController {
    @Value("${business.storage.enabled}")
    private Boolean enabled;
    private Random random = new Random();

    @GetMapping("/{productId}")
    StorageResponse getProductStorage(@PathVariable String productId) {
        int randomInt = random.nextInt();
        if(randomInt % 3 == 0) {
            throw new RuntimeException("random exception");
        }
        return StorageResponse.builder()
                .id(productId)
                .enabled(enabled)
                .name("name" + random.nextInt())
                .count(1000)
                .build();
    }
}
