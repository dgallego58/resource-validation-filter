package co.com.dgallego58.user;

import java.io.Serializable;


public interface Identifiable<T extends Serializable, S extends BaseEntity<T>> {

    T getId();

    S setId(T id);

}
