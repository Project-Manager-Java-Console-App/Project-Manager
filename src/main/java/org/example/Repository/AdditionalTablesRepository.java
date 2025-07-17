package org.example.Repository;

import java.util.List;

public interface AdditionalTablesRepository<E> {
    boolean delete(E id, E userId);

    boolean save(E id, E userId);

    List<E> getUsersIn(E id);

    List<E> getAllAddedToUser(E userId);
}
