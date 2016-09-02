package org.evgndev.repository;

import org.evgndev.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgndev on 21.08.16.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long>  {
    List<Image> findAll();
}
