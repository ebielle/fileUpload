package com.example.fileUploadAndDownload.repositories;

import com.example.fileUploadAndDownload.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
