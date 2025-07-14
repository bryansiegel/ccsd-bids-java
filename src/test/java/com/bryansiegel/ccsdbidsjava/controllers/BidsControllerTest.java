package com.bryansiegel.ccsdbidsjava.controllers;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import com.bryansiegel.ccsdbidsjava.services.BidsService;
import com.bryansiegel.ccsdbidsjava.services.SubContractorListingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BidsController.class)
class BidsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidsService bidsService;

    @MockBean
    private SubContractorListingService subContractorListingService;

    private Bids testBid;
    private List<Bids> testBidsList;

    @BeforeEach
    void setUp() {
        testBid = new Bids();
        testBid.setId(1L);
        testBid.setContractName("Test Contract");
        testBid.setMpidNumber("MPID123");
        testBid.setActive(true);

        testBidsList = Arrays.asList(testBid);
    }

    @Test
    @WithMockUser
    void listBids_ShouldReturnBidsListView() throws Exception {
        when(bidsService.findAll()).thenReturn(testBidsList);

        mockMvc.perform(get("/admin/bids"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/bids/list"))
                .andExpect(model().attributeExists("bidsList"))
                .andExpect(model().attribute("bidsList", testBidsList));

        verify(bidsService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void createBidForm_ShouldReturnCreateView() throws Exception {
        mockMvc.perform(get("/admin/bids/create"))
                .andExpect(status().isOk())
                .andExpected(view().name("admin/bids/create"))
                .andExpect(model().attributeExists("bid"));
    }

    @Test
    @WithMockUser
    void createBid_ShouldSaveBidAndRedirect() throws Exception {
        MockMultipartFile advertisementFile = new MockMultipartFile("advertisementForBidsFile", "test.pdf", "application/pdf", "test content".getBytes());
        MockMultipartFile preBidFile = new MockMultipartFile("preBidSignInSheetFile", "prebid.pdf", "application/pdf", "prebid content".getBytes());
        MockMultipartFile bidTabFile = new MockMultipartFile("bidTabulationSheetFile", "bidtab.pdf", "application/pdf", "bidtab content".getBytes());

        mockMvc.perform(multipart("/admin/bids/create")
                .file(advertisementFile)
                .file(preBidFile)
                .file(bidTabFile)
                .param("contractName", "Test Contract")
                .param("mpidNumber", "MPID123")
                .param("active", "true")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/bids"));

        verify(bidsService, times(1)).save(any(Bids.class));
    }

    @Test
    @WithMockUser
    void editBidForm_ShouldReturnEditView() throws Exception {
        when(bidsService.findById(1L)).thenReturn(testBid);

        mockMvc.perform(get("/admin/bids/edit/1"))
                .andExpected(status().isOk())
                .andExpected(view().name("admin/bids/edit"))
                .andExpected(model().attributeExists("bid"))
                .andExpected(model().attribute("bid", testBid));

        verify(bidsService, times(1)).findById(1L);
    }

    @Test
    @WithMockUser
    void deleteBid_ShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(get("/admin/bids/delete/1"))
                .andExpected(status().is3xxRedirection())
                .andExpected(redirectedUrl("/admin/bids"));

        verify(bidsService, times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser
    void viewBid_WhenBidExists_ShouldReturnViewPage() throws Exception {
        List<SubContractorListing> subContractors = Arrays.asList(new SubContractorListing());
        when(bidsService.findById(1L)).thenReturn(testBid);
        when(subContractorListingService.findByBidId(1L)).thenReturn(subContractors);

        mockMvc.perform(get("/admin/bids/1/view"))
                .andExpected(status().isOk())
                .andExpected(view().name("admin/bids/view"))
                .andExpected(model().attributeExists("bid"))
                .andExpected(model().attributeExists("subContractors"));

        verify(bidsService, times(1)).findById(1L);
        verify(subContractorListingService, times(1)).findByBidId(1L);
    }

    @Test
    @WithMockUser
    void viewBid_WhenBidNotFound_ShouldThrowException() throws Exception {
        when(bidsService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/admin/bids/999/view"))
                .andExpected(status().isNotFound());

        verify(bidsService, times(1)).findById(999L);
    }

    @Test
    @WithMockUser
    void downloadAdvertisementForBids_ShouldReturnPdf() throws Exception {
        testBid.setAdvertisementForBids("test pdf content".getBytes());
        when(bidsService.findById(1L)).thenReturn(testBid);

        mockMvc.perform(get("/admin/bids/download/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "inline; filename=\"Ad-for-Bids.pdf\""));

        verify(bidsService, times(1)).findById(1L);
    }
}