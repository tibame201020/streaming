package com.custom.stream.repo;

import com.custom.stream.model.gimy.GimyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface GimyHistoryRepo extends JpaRepository<GimyHistory, Timestamp> {
}
