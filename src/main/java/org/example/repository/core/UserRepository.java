package org.example.repository.core;


import org.example.model.Users;

public interface UserRepository extends CoreCrudRepository<Users> {

    Users authenticate(String username, String password);
}
