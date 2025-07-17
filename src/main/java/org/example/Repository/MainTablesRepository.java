package org.example.Repository;

public interface MainTablesRepository<T> {
    T save(T t);

    boolean delete(T t);

    T update(T t, int id);

    T findById(Integer id);

    T findByName(String name);
}
