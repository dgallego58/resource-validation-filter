package co.com.bancolombia.bd;

import co.com.bancolombia.user.BaseEntity;
import co.com.bancolombia.user.Identifiable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public enum BDSimulatorService {

    INSTANCE("memory") {
        @Override
        public <T extends Serializable, E extends BaseEntity<T>> E save(E entity) {
            return (E) getCollector().put(entity.getId().toString(), entity);
        }

        @Override
        public <T extends Serializable, E extends BaseEntity<T>> E findById(T entityId) {
            return (E) getCollector().get(entityId.toString());
        }
    };

    private final Map<String, ? super Identifiable<? extends Serializable, ? extends BaseEntity>> collector;
    private final String bdType;

    BDSimulatorService(String bdType) {
        this.bdType = bdType;
        this.collector = new HashMap<>();
    }

    public abstract <T extends Serializable, E extends BaseEntity<T>> E save(E entity);

    public abstract <T extends Serializable, E extends BaseEntity<T>> E findById(T entityId);


    public Map<String, ? super Identifiable<? extends Serializable, ? extends BaseEntity>> getCollector() {
        return collector;
    }
}
