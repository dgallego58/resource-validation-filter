package co.com.bancolombia;

import bancolombia.properties.GroupPath;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JwtFilterTest {

    private static final Logger log = LoggerFactory.getLogger(JwtFilterTest.class);

    @Test
    void testBool() {
        var paths = new JwtFilter(GroupPath.read()).getPathsForGroup(GroupPath.GroupType.ADMIN);
        log.info("Paths {}", paths);
    }
}
