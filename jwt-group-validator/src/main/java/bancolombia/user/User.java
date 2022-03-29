package bancolombia.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
public class User extends BaseEntity<UUID> {

    private UUID id;
    private String name;
    private Set<Role> roles;

}
