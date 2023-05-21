package com.example.bookhotel.repositories;

import com.example.bookhotel.entities.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<TaiKhoan, String> {

    Optional<TaiKhoan> findByEmail(String email);
    Page<TaiKhoan> findByNameContainingOrEmailContainingAllIgnoreCase(String name, String email, Pageable pageable);
    @Query(value = "{'email': ?0}")
    Optional<TaiKhoan> getUser(String email);

    @Query(value = "{'email': ?0}", exists = true)
    boolean checkEmail(String email);

    @Override
    void deleteById(String s);
    boolean existsByEmail(String email);
    Optional<TaiKhoan> findById(String id);

    List<TaiKhoan> findAll();

    @Query(value = "{'trangThai': true}")
    List<TaiKhoan> getAlls();

}
