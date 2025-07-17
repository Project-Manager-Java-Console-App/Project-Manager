package org.example.Repository;


import org.example.model.Users;

public interface UserRepository extends MainTablesRepository<Users> {

    Users authenticate(String username, char[] password);
}
