package bancolombia.user;

import bancolombia.properties.GroupPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
public class Role extends BaseEntity<UUID> {

    private UUID id;
    private GroupPath.GroupType name;

}
