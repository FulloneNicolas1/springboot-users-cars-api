package com.api.demo.repositories;

import com.api.demo.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICarRepository extends JpaRepository<CarModel, Long> {

    List<CarModel> findByUserId(Long userId);
}