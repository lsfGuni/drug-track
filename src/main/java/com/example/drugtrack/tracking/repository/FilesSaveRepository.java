package com.example.drugtrack.tracking.repository;

import com.example.drugtrack.tracking.entity.FilesSave;
import org.springframework.data.repository.CrudRepository;
/**
 * FilesSaveRepository는 FilesSave 엔티티와 상호작용하는 JPA 레포지토리입니다.
 */
public interface FilesSaveRepository extends CrudRepository<FilesSave, Long> {

}

