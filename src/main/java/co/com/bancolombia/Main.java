package co.com.bancolombia;

import co.com.bancolombia.bd.BDSimulatorService;
import co.com.bancolombia.properties.GroupPath;
import co.com.bancolombia.service.ValidationFilterService;
import co.com.bancolombia.user.Role;
import co.com.bancolombia.user.User;
import co.com.bancolombia.util.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // ---------- check reading json ----------
        GroupPath groupPath = GroupPath.read();
        log.info("GroupPath {}", groupPath.getGroupPaths());
        var isInMatch = groupPath.matchPathForGroup(GroupPath.GroupType.COMERCIAL, "/download/resource/hola.txt");
        log.info("Match for Commercial? {}", isInMatch);

        //----------------Start process-----------

        User userA = User.create()
                         .setName("Test")
                         .setId(UUID.randomUUID())
                         .setRoles(Set.of(Role.create()
                                              .setName(GroupPath.GroupType.ADMIN)
                                              .setId(UUID.randomUUID()))
                         );
        User userB = User.create()
                         .setName("Test2")
                         .setId(UUID.randomUUID())
                         .setRoles(Set.of(Role.create()
                                              .setName(GroupPath.GroupType.COMERCIAL)
                                              .setId(UUID.randomUUID()))
                         );

        var bdService = ValidationFilterService.createDefault();
        bdService.save(userA);
        bdService.save(userB);

        BDSimulatorService instance = BDSimulatorService.INSTANCE;
        log.info("Print DB {}", instance.getCollector());

        var pinnedUser = bdService.findBy(userA.getId());
        log.info("DB WORKS? DB {}", pinnedUser.getName());

        // ------------ emulate service filter for UserA SHOULD NOT BE PERMITTED
        String requestPath = "/download/custom-path/file?text=a";
        var userId = extractIdForUserInJwt(userA);
        var user = bdService.findBy(UUID.fromString(userId));
        var userGroups = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        var data = groupPath.isSecuredForRoles(userGroups, requestPath);
        log.info("SHOULD BE PASSED THROUGH SERVICE FOR A? {}", data ? "YES" : "NO");

        // --------------- FOR COMERCIAL SHOULD BE PERMITTED;
        var userIdB = extractIdForUserInJwt(userB);
        var userBFromDb = bdService.findBy(UUID.fromString(userIdB));
        var userGroupsOfB = userBFromDb.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        var dataForB = groupPath.isSecuredForRoles(userGroupsOfB, requestPath);
        log.info("SHOULD BE PASSED THROUGH SERVICE FOR B? {}", dataForB ? "YES" : "NO");

        // ------------- FOR C being a SUPER USER HE HAS ROLES COMERCIAL AND ADMIN SHOULD BE PERMITTED

        User userC = User.create()
                         .setName("Test2")
                         .setId(UUID.randomUUID())
                         .setRoles(Set.of(Role.create()
                                              .setName(GroupPath.GroupType.COMERCIAL)
                                              .setId(UUID.randomUUID()),
                                         Role.create()
                                             .setName(GroupPath.GroupType.ADMIN)
                                             .setId(UUID.randomUUID())
                                 )
                         );
        bdService.save(userC);

        var userIdC = extractIdForUserInJwt(userC);

        var userCFromDb = bdService.findBy(UUID.fromString(userIdC));
        var userGroupsOfC = userCFromDb.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        var dataForC = groupPath.isSecuredForRoles(userGroupsOfC, requestPath);
        log.info("SHOULD BE PASSED THROUGH SERVICE FOR C? {}", dataForC ? "YES" : "NO");
    }


    static String extractIdForUserInJwt(User user) {
        var requestClaims = new HashMap<String, String>();
        requestClaims.put("id", user.getId().toString());
        String jwts = "Bearer " + Jwts.builder()
                                      .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                                      .setClaims(requestClaims)
                                      .compact();
        JwtService<User> jwtParser = new JwtService<>(jwts);
        var signedUser = jwtParser.asType(User.class);
        log.info("{}", signedUser.getId());
        return signedUser.getId().toString();
    }
}
