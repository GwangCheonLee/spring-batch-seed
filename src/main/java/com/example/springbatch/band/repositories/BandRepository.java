package com.example.springbatch.band.repositories;

import com.example.springbatch.band.entities.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {
}
