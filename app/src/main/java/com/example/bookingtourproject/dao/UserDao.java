package com.example.bookingtourproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookingtourproject.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    // Insert a new user
    @Insert
    void insert(User user);

    // Update an existing user
    @Update
    void update(User user);

    // Delete a user
    @Delete
    void delete(User user);

    // Get a user by ID
    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    // Get all users
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT EXISTS(SELECT * FROM user WHERE email = :email)")
    boolean isTaken(String email);

    @Query("SELECT EXISTS(SELECT * FROM user WHERE email = :email AND password = :password)")
    boolean login(String email, String password);

    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User getUserByEmailAndPassword(String email, String password);

    @Query("UPDATE user SET password = :password, fullName = :fullName, phone = :phone, email = :email, role = :role, address = :address WHERE id = :userId")
    void updateUserById(int userId, String fullName, String email,String password, String phone, String role, String address);
}
