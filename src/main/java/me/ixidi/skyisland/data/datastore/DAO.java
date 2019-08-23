package me.ixidi.skyisland.data.datastore;

public interface DAO<T> {

    T load();

    void save(T value);

}
