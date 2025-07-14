package com.bryansiegel.ccsdbidsjava.repositories;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BidsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BidsRepository bidsRepository;

    private Bids testBid;

    @BeforeEach
    void setUp() {
        testBid = new Bids();
        testBid.setContractName("Test Contract");
        testBid.setMpidNumber("MPID123");
        testBid.setActive(true);
    }

    @Test
    void save_ShouldPersistBid() {
        Bids savedBid = bidsRepository.save(testBid);

        assertNotNull(savedBid.getId());
        assertEquals("Test Contract", savedBid.getContractName());
        assertEquals("MPID123", savedBid.getMpidNumber());
        assertTrue(savedBid.isActive());
    }

    @Test
    void findById_WhenBidExists_ShouldReturnBid() {
        Bids savedBid = entityManager.persistAndFlush(testBid);

        Optional<Bids> foundBid = bidsRepository.findById(savedBid.getId());

        assertTrue(foundBid.isPresent());
        assertEquals("Test Contract", foundBid.get().getContractName());
    }

    @Test
    void findById_WhenBidDoesNotExist_ShouldReturnEmpty() {
        Optional<Bids> foundBid = bidsRepository.findById(999L);

        assertFalse(foundBid.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllBids() {
        Bids bid1 = new Bids();
        bid1.setContractName("Contract 1");
        bid1.setMpidNumber("MPID001");
        
        Bids bid2 = new Bids();
        bid2.setContractName("Contract 2");
        bid2.setMpidNumber("MPID002");

        entityManager.persistAndFlush(bid1);
        entityManager.persistAndFlush(bid2);

        List<Bids> allBids = bidsRepository.findAll();

        assertEquals(2, allBids.size());
    }

    @Test
    void deleteById_ShouldRemoveBid() {
        Bids savedBid = entityManager.persistAndFlush(testBid);
        Long bidId = savedBid.getId();

        bidsRepository.deleteById(bidId);

        Optional<Bids> deletedBid = bidsRepository.findById(bidId);
        assertFalse(deletedBid.isPresent());
    }
}