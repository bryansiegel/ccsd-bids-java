package com.bryansiegel.ccsdbidsjava.services;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.repositories.BidsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidsServiceTest {

    @Mock
    private BidsRepository bidsRepository;

    @InjectMocks
    private BidsService bidsService;

    private Bids testBid;

    @BeforeEach
    void setUp() {
        testBid = new Bids();
        testBid.setId(1L);
        testBid.setContractName("Test Contract");
        testBid.setMpidNumber("MPID123");
        testBid.setActive(true);
    }

    @Test
    void findAll_ShouldReturnAllBids() {
        Bids bid1 = new Bids();
        bid1.setId(1L);
        bid1.setContractName("Contract 1");
        
        Bids bid2 = new Bids();
        bid2.setId(2L);
        bid2.setContractName("Contract 2");
        
        List<Bids> expectedBids = Arrays.asList(bid1, bid2);
        when(bidsRepository.findAll()).thenReturn(expectedBids);

        List<Bids> actualBids = bidsService.findAll();

        assertEquals(2, actualBids.size());
        assertEquals(expectedBids, actualBids);
        verify(bidsRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenBidExists_ShouldReturnBid() {
        when(bidsRepository.findById(1L)).thenReturn(Optional.of(testBid));

        Bids result = bidsService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Contract", result.getContractName());
        verify(bidsRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenBidDoesNotExist_ShouldReturnNull() {
        when(bidsRepository.findById(anyLong())).thenReturn(Optional.empty());

        Bids result = bidsService.findById(999L);

        assertNull(result);
        verify(bidsRepository, times(1)).findById(999L);
    }

    @Test
    void save_ShouldCallRepositorySave() {
        when(bidsRepository.save(any(Bids.class))).thenReturn(testBid);

        bidsService.save(testBid);

        verify(bidsRepository, times(1)).save(testBid);
    }

    @Test
    void deleteById_ShouldCallRepositoryDeleteById() {
        doNothing().when(bidsRepository).deleteById(1L);

        bidsService.deleteById(1L);

        verify(bidsRepository, times(1)).deleteById(1L);
    }
}