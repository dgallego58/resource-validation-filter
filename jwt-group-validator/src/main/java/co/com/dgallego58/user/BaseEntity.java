package co.com.dgallego58.user;

import java.io.Serializable;

public abstract class BaseEntity<T extends Serializable> implements Identifiable<T, BaseEntity<T>> {

}
