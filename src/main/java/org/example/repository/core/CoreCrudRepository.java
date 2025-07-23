package org.example.repository.core;

public interface CoreCrudRepository<T> {
    T save(T t);

    boolean delete(T t);

    T update(T t, int id);

    T findById(Integer id);

    T findByName(String name);
}
