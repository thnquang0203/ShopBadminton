package com.shopbadminton.repository;

import com.shopbadminton.entity.BadmintonCourt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadmintonCourtRepository extends JpaRepository<BadmintonCourt, Integer> {
}