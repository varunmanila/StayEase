package com.example.StayEase.Repocitory;

import com.example.StayEase.Entity.Hotel;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepocitory extends JpaRepository<Hotel, Integer> {
}
