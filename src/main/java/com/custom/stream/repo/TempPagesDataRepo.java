package com.custom.stream.repo;

import com.custom.stream.model.gimy.TempPagesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempPagesDataRepo extends JpaRepository<TempPagesData, Long> {
}
