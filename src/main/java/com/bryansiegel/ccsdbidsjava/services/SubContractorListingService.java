package com.bryansiegel.ccsdbidsjava.services;

import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import com.bryansiegel.ccsdbidsjava.repositories.SubContractorListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubContractorListingService {

    @Autowired
    private SubContractorListingRepository subContractorListingRepository;

    public List<SubContractorListing> findAll() {
        return subContractorListingRepository.findAll();
    }

    public SubContractorListing findById(Long id) {
        return subContractorListingRepository.findById(id).orElse(null);
    }

    public void save(SubContractorListing subContractorListing) {
        subContractorListingRepository.save(subContractorListing);
    }

    public void deleteById(Long id) {
        subContractorListingRepository.deleteById(id);
    }

    public List<SubContractorListing> findByBidId(Long bidId) {
        return subContractorListingRepository.findByBidsId(bidId);
    }
}