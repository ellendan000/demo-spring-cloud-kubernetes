package open.ending.mss.inventory.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StorageResponse {
    private String id;
    private String name;
    private Integer count;
    private Boolean enabled;
}
