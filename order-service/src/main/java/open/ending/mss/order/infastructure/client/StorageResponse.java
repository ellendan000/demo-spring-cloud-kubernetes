package open.ending.mss.order.infastructure.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StorageResponse {
    private String id;
    private Boolean enabled;
}
