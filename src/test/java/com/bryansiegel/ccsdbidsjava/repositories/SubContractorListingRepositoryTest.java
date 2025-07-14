package com.bryansiegel.ccsdbidsjava.repositories;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubContractorListingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubContractorListingRepository subContractorListingRepository;

    private Bids testBid;
    private SubContractorListing testSubContractor;

    @BeforeEach
    void setUp() {
        testBid = new Bids();
        testBid.setContractName("Test Contract");
        testBid.setMpidNumber("MPID123");
        testBid.setActive(true);

        testSubContractor = new SubContractorListing();
        testSubContractor.setSubContractorCompanyName("Test Company");
        testSubContractor.setSubContractorDocumentId("DOC123");
        testSubContractor.setBids(testBid);
    }

    @Test
    void save_ShouldPersistSubContractor() {
        Bids savedBid = entityManager.persistAndFlush(testBid);
        testSubContractor.setBids(savedBid);

        SubContractorListing savedSubContractor = subContractorListingRepository.save(testSubContractor);

        assertNotNull(savedSubContractor.getId());
        assertEquals("Test Company", savedSubContractor.getSubContractorCompanyName());
        assertEquals("DOC123", savedSubContractor.getSubContractorDocumentId());
        assertEquals(savedBid.getId(), savedSubContractor.getBids().getId());
    }

    @Test
    void findById_WhenSubContractorExists_ShouldReturnSubContractor() {
        Bids savedBid = entityManager.persistAndFlush(testBid);
        testSubContractor.setBids(savedBid);
        SubContractorListing savedSubContractor = entityManager.persistAndFlush(testSubContractor);

        Optional<SubContractorListing> foundSubContractor = subContractorListingRepository.findById(savedSubContractor.getId());

        assertTrue(foundSubContractor.isPresent());
        assertEquals("Test Company", foundSubContractor.get().getSubContractorCompanyName());
    }

    @Test
    void findById_WhenSubContractorDoesNotExist_ShouldReturnEmpty() {
        Optional<SubContractorListing> foundSubContractor = subContractorListingRepository.findById(999L);

        assertFalse(foundSubContractor.isPresent());
    }

    @Test
    void findByBidsId_ShouldReturnSubContractorsForBid() {
        Bids savedBid = entityManager.persistAndFlush(testBid);
        
        SubContractorListing sub1 = new SubContractorListing();
        sub1.setSubContractorCompanyName("Company 1");
        sub1.setBids(savedBid);
        
        SubContractorListing sub2 = new SubContractorListing();
        sub2.setSubContractorCompanyName("Company 2");
        sub2.setBids(savedBid);

        entityManager.persistAndFlush(sub1);
        entityManager.persistAndFlush(sub2);

        List<SubContractorListing> foundSubContractors = subContractorListingRepository.findByBidsId(savedBid.getId());

        assertEquals(2, foundSubContractors.size());
        assertTrue(foundSubContractors.stream().anyMatch(s -> "Company 1".equals(s.getSubContractorCompanyName())));
        assertTrue(foundSubContractors.stream().anyMatch(s -> "Company 2".equals(s.getSubContractorCompanyName())));
    }

    @Test
    void findByBidsId_WhenNoBidsFound_ShouldReturnEmptyList() {
        List<SubContractorListing> foundSubContractors = subContractorListingRepository.findByBidsId(999L);

        assertTrue(foundSubContractors.isEmpty());
    }

    @Test
    void deleteById_ShouldRemoveSubContractor() {
        Bids savedBid = entityManager.persistAndFlush(testBid);
        testSubContractor.setBids(savedBid);
        SubContractorListing savedSubContractor = entityManager.persistAndFlush(testSubContractor);
        Long subContractorId = savedSubContractor.getId();

        subContractorListingRepository.deleteById(subContractorId);

        Optional<SubContractorListing> deletedSubContractor = subContractorListingRepository.findById(subContractorId);
        assertFalse(deletedSubContractor.isPresent());
    }
}