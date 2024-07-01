package com.agenceWebSite.AgenceWebSite.repository;


import com.agenceWebSite.AgenceWebSite.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long> {

    Files findByName(String name);
}
