package com.bryansiegel.ccsdbidsjava.services;

import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import com.bryansiegel.ccsdbidsjava.repositories.SubContractorListingRepository;
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
class SubContractorListingServiceTest {

    @Mock
    private SubContractorListingRepository subContractorListingRepository;

    @InjectMocks
    private SubContractorListingService subContractorListingService;

    private SubContractorListing testSubContractor;

    @BeforeEach
    void setUp() {
        testSubContractor = new SubContractorListing();
        testSubContractor.setId(1L);
        testSubContractor.setSubContractorCompanyName("Test Company");
        testSubContractor.setSubContractorDocumentId("DOC123");
    }

    @Test
    void findAll_ShouldReturnAllSubContractors() {
        SubContractorListing sub1 = new SubContractorListing();
        sub1.setId(1L);
        sub1.setSubContractorCompanyName("Company 1");
        
        SubContractorListing sub2 = new SubContractorListing();
        sub2.setId(2L);
        sub2.setSubContractorCompanyName("Company 2");
        
        List<SubContractorListing> expectedSubContractors = Arrays.asList(sub1, sub2);
        when(subContractorListingRepository.findAll()).thenReturn(expectedSubContractors);

        List<SubContractorListing> actualSubContractors = subContractorListingService.findAll();

        assertEquals(2, actualSubContractors.size());
        assertEquals(expectedSubContractors, actualSubContractors);
        verify(subContractorListingRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenSubContractorExists_ShouldReturnSubContractor() {
        when(subContractorListingRepository.findById(1L)).thenReturn(Optional.of(testSubContractor));

        SubContractorListing result = subContractorListingService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Company", result.getSubContractorCompanyName());
        verify(subContractorListingRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenSubContractorDoesNotExist_ShouldReturnNull() {
        when(subContractorListingRepository.findById(anyLong())).thenReturn(Optional.empty());

        SubContractorListing result = subContractorListingService.findById(999L);

        assertNull(result);
        verify(subContractorListingRepository, times(1)).findById(999L);
    }

    @Test
    void save_ShouldCallRepositorySave() {
        when(subContractorListingRepository.save(any(SubContractorListing.class))).thenReturn(testSubContractor);

        subContractorListingService.save(testSubContractor);

        verify(subContractorListingRepository, times(1)).save(testSubContractor);
    }

    @Test
    void deleteById_ShouldCallRepositoryDeleteById() {
        doNothing().when(subContractorListingRepository).deleteById(1L);

        subContractorListingService.deleteById(1L);

        verify(subContractorListingRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByBidId_ShouldReturnSubContractorsForBid() {
        Long bidId = 1L;
        List<SubContractorListing> expectedSubContractors = Arrays.asList(testSubContractor);
        when(subContractorListingRepository.findByBidsId(bidId)).thenReturn(expectedSubContractors);

        List<SubContractorListing> result = subContractorListingService.findByBidId(bidId);

        assertEquals(1, result.size());
        assertEquals(expectedSubContractors, result);
        verify(subContractorListingRepository, times(1)).findByBidsId(bidId);
    }
}