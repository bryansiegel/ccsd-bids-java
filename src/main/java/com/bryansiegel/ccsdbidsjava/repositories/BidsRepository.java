package com.bryansiegel.ccsdbidsjava.repositories;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidsRepository extends JpaRepository<Bids, Long> {
}