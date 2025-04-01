package com.bryansiegel.ccsdbidsjava.repositories;

import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubContractorListingRepository extends JpaRepository<SubContractorListing, Long> {
}