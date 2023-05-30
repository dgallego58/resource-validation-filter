package co.com.dgallego58.bd;

import co.com.dgallego58.user.BaseEntity;
import co.com.dgallego58.user.Identifiable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public enum DataBaseEmulator {

    INSTANCE("memory") {
        @Override
        public <T extends Serializable, E extends BaseEntity<T>> E save(E entity) {
            return (E) getCollector().put(entity.getId(), entity);
        }

        @Override
        public <T extends Serializable, E extends BaseEntity<T>> E findById(T entityId) {
            return (E) getCollector().get(entityId);
        }
    };

    private final Map<? super Serializable, ? super Identifiable<? extends Serializable, ? extends BaseEntity>> collector;
    private final String bdType;

    DataBaseEmulator(String bdType) {
        this.bdType = bdType;
        this.collector = new HashMap<>();
    }

    public String getBdType() {
        return bdType;
    }

    public abstract <T extends Serializable, E extends BaseEntity<T>> E save(E entity);

    public abstract <T extends Serializable, E extends BaseEntity<T>> E findById(T entityId);


    public Map<? super Serializable, ? super Identifiable<? extends Serializable, ? extends BaseEntity>> getCollector() {
        return collector;
    }
}
