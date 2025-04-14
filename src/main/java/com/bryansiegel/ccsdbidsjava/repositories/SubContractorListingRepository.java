package com.bryansiegel.ccsdbidsjava.repositories;

import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubContractorListingRepository extends JpaRepository<SubContractorListing, Long> {
    List<SubContractorListing> findByBidsId(Long bidId);

}