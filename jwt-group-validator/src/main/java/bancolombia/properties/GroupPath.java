package bancolombia.properties;

import bancolombia.util.JacksonUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Builder
@Jacksonized
@Getter
@Slf4j
public class GroupPath {

    @Builder.Default
    private Map<GroupType, List<String>> groupPaths = new EnumMap<>(GroupType.class);

    public static GroupPath read() {
        try {
            var jsonFile = Thread.currentThread().getContextClassLoader().getResource("admin-group.json");
            return JacksonUtil.jsonMapper.readValue(jsonFile, GroupPath.class);
        } catch (IOException e) {
            log.error("GroupPath Could not be interpreted with that json");
            throw new WrongMappingException("Wrong mapping", e);
        }
    }

    public boolean isSecuredForRoles(Collection<GroupType> groups, String requestPath) {

        List<String> paths = new ArrayList<>();
        groups.forEach(groupType -> paths.addAll(this.groupPaths.get(groupType)));
        log.info("Paths {}", paths);
        PathPatternParser parser = new PathPatternParser();
        parser.setMatchOptionalTrailingSeparator(true);
        PathContainer pc = PathContainer.parsePath(requestPath);
        return paths.stream()
                    .map(parser::parse)
                    .anyMatch(pattern -> pattern.matches(pc));
    }

    public boolean matchPathForGroup(GroupType group, String requestPath) {
        PathPatternParser parser = new PathPatternParser();
        parser.setMatchOptionalTrailingSeparator(true);
        PathContainer pc = PathContainer.parsePath(requestPath);
        return groupPaths.getOrDefault(group, new ArrayList<>())
                         .stream()
                         .map(parser::parse)
                         .anyMatch(patternParser -> patternParser.matches(pc));
    }

    public List<String> availablePathsFor(GroupType group) {
        return this.groupPaths.putIfAbsent(group, new ArrayList<>());
    }


    public boolean isSecured(String path) {
        PathPatternParser parser = new PathPatternParser();
        parser.setMatchOptionalTrailingSeparator(true);
        PathContainer pc = PathContainer.parsePath(path);
        return groupPaths.values()
                         .stream()
                         .flatMap(Collection::stream)
                         .map(parser::parse)
                         .anyMatch(pattern -> pattern.matches(pc));
    }

    public enum GroupType {
        ADMIN,
        COMERCIAL
    }

    public static class WrongMappingException extends RuntimeException {
        public WrongMappingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
