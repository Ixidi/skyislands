package me.ixidi.skyisland.data;

public interface DataStorage<T> {

    T load();

    void save(T value);


}
