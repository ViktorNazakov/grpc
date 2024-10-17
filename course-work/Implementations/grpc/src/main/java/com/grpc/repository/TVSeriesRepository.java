package com.grpc.repository;

import com.grpc.entity.TVSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVSeriesRepository extends JpaRepository<TVSeries, Integer> {
}
