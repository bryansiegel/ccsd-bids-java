package com.bryansiegel.ccsdbidsjava.controllers;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import com.bryansiegel.ccsdbidsjava.services.BidsService;
import com.bryansiegel.ccsdbidsjava.services.SubContractorListingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/bids")
public class BidsController {

    private final BidsService bidsService;
    private final SubContractorListingService subContractorListingService;

    public BidsController(BidsService bidsService, SubContractorListingService subContractorListingService) {
        this.bidsService = bidsService;
        this.subContractorListingService = subContractorListingService;
    }

    //In order to make subContractorDocument file upload work
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @GetMapping
    public String listBids(Model model) {
        List<Bids> bidsList = bidsService.findAll();
        model.addAttribute("bidsList", bidsList);
        return "admin/bids/list";
    }

    @GetMapping("/create")
    public String createBidForm(Model model) {
        model.addAttribute("bid", new Bids());
        return "admin/bids/create";
    }

    @PostMapping("/create")
    public String createBid(@ModelAttribute Bids bid,
                            @RequestParam("advertisementForBidsFile") MultipartFile advertisementFile,
                            @RequestParam("preBidSignInSheetFile") MultipartFile preBidFile,
                            @RequestParam("bidTabulationSheetFile") MultipartFile bidTabFile) throws IOException {
        if (!advertisementFile.isEmpty()) {
            bid.setAdvertisementForBids(advertisementFile.getBytes());
        }
        if (!preBidFile.isEmpty()) {
            bid.setPreBidSignInSheet(preBidFile.getBytes());
        }
        if (!bidTabFile.isEmpty()) {
            bid.setBidTabulationSheet(bidTabFile.getBytes());
        }
        bidsService.save(bid);
        return "redirect:/admin/bids";
    }

    @GetMapping("/edit/{id}")
    public String editBidForm(@PathVariable Long id, Model model) {
        Bids bid = bidsService.findById(id);
        model.addAttribute("bid", bid);
        return "admin/bids/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBid(@PathVariable Long id,
                          @ModelAttribute Bids bid,
                          @RequestParam("advertisementForBidsFile") MultipartFile advertisementFile,
                          @RequestParam("preBidSignInSheetFile") MultipartFile preBidFile,
                          @RequestParam("bidTabulationSheetFile") MultipartFile bidTabFile) throws IOException {
        // Fetch the existing bid from the database
        Bids existingBid = bidsService.findById(id);

        // Update fields
        existingBid.setContractName(bid.getContractName());
        existingBid.setMpidNumber(bid.getMpidNumber());
        existingBid.setActive(bid.isActive());

        // Update files only if new files are uploaded
        if (!advertisementFile.isEmpty()) {
            existingBid.setAdvertisementForBids(advertisementFile.getBytes());
        }
        if (!preBidFile.isEmpty()) {
            existingBid.setPreBidSignInSheet(preBidFile.getBytes());
        }
        if (!bidTabFile.isEmpty()) {
            existingBid.setBidTabulationSheet(bidTabFile.getBytes());
        }

        // Save the updated bid
        bidsService.save(existingBid);

        return "redirect:/admin/bids";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable Long id) {
        bidsService.deleteById(id);
        return "redirect:/admin/bids";
    }

    @GetMapping("/{bidId}/view")
    public String viewBid(@PathVariable Long bidId, Model model) {
        Bids bid = bidsService.findById(bidId);
        if (bid == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bid not found");
        }

        List<SubContractorListing> subContractors = subContractorListingService.findByBidId(bidId);

        model.addAttribute("bid", bid);
        model.addAttribute("subContractors", subContractors);

        return "admin/bids/view";
    }

    // SubContractors
    @GetMapping("/{bidId}/subcontractors")
    public String listSubContractors(@PathVariable Long bidId, Model model) {
        Bids bid = bidsService.findById(bidId);
        model.addAttribute("bid", bid);
        model.addAttribute("subContractorListings", bid.getSubContractorListings());
        return "admin/subcontractors/list";
    }

    @GetMapping("/{bidId}/subcontractors/create")
    public String createSubContractorForm(@PathVariable Long bidId, Model model) {
        SubContractorListing subContractorListing = new SubContractorListing();
        subContractorListing.setBids(bidsService.findById(bidId));
        model.addAttribute("subContractorListing", subContractorListing);
        return "admin/subcontractors/create";
    }

    @PostMapping("/{bidId}/subcontractors/create")
    public String createSubContractor(@PathVariable Long bidId,
                                      @ModelAttribute SubContractorListing subContractorListing,
                                      @RequestParam("subContractorDocument") MultipartFile subContractfile) throws IOException {
        // Fetch the associated bid
        Bids bid = bidsService.findById(bidId);
        subContractorListing.setBids(bid);

        // Explicitly handle the file upload
        if (subContractfile != null && !subContractfile.isEmpty()) {

            subContractorListing.setSubContractorDocument(subContractfile.getBytes());
        }

        // Save the subcontractor listing
        subContractorListingService.save(subContractorListing);

        return "redirect:/admin/bids/" + bidId + "/subcontractors";
    }


    @GetMapping("/{bidId}/subcontractors/edit/{id}")
    public String editSubContractorForm(@PathVariable Long bidId, @PathVariable Long id, Model model) {
        SubContractorListing subContractorListing = subContractorListingService.findById(id);
        model.addAttribute("subContractorListing", subContractorListing);
        return "admin/subcontractors/edit";
    }

    @PostMapping("/{bidId}/subcontractors/edit/{id}")
    public String editSubContractor(@PathVariable Long bidId, @PathVariable Long id,
                                    @ModelAttribute SubContractorListing subContractorListing,
                                    @RequestParam("subContractorDocument") MultipartFile file) throws IOException {
        // Fetch the existing subcontractor listing from the database
        SubContractorListing existingSubContractor = subContractorListingService.findById(id);

        // Update fields
        existingSubContractor.setSubContractorCompanyName(subContractorListing.getSubContractorCompanyName());
        existingSubContractor.setSubContractorDocumentId(subContractorListing.getSubContractorDocumentId());
        existingSubContractor.setSubContractorDocumentUrl(subContractorListing.getSubContractorDocumentUrl());

        // Update file only if a new file is uploaded
        if (file != null && !file.isEmpty()) {
            existingSubContractor.setSubContractorDocument(file.getBytes());
        }

        // Save the updated subcontractor listing
        subContractorListingService.save(existingSubContractor);

        return "redirect:/admin/bids/" + bidId + "/subcontractors";
    }

    @GetMapping("/{bidId}/subcontractors/delete/{id}")
    public String deleteSubContractor(@PathVariable Long bidId, @PathVariable Long id) {
        subContractorListingService.deleteById(id);
        return "redirect:/admin/bids/" + bidId + "/subcontractors";
    }

    // Downloads
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAdvertisementForBids(@PathVariable Long id) {
        Bids bid = bidsService.findById(id);
        byte[] document = bid.getAdvertisementForBids();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("Ad-for-Bids.pdf").build());
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }

    @GetMapping("/download/preBid/{id}")
    public ResponseEntity<byte[]> downloadPreBidSignInSheet(@PathVariable Long id) {
        Bids bid = bidsService.findById(id);
        byte[] document = bid.getPreBidSignInSheet();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("Pre-Bid-Sign-In-Sheet.pdf").build());
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }

    @GetMapping("/download/bidTab/{id}")
    public ResponseEntity<byte[]> downloadBidTabulationSheet(@PathVariable Long id) {
        Bids bid = bidsService.findById(id);
        byte[] document = bid.getBidTabulationSheet();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("Bid-Tabulation-Sheet.pdf").build());
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }

    @GetMapping("/{bidId}/subcontractors/download/{id}")
    public ResponseEntity<byte[]> downloadSubContractorDocument(@PathVariable Long bidId, @PathVariable Long id) {
        SubContractorListing subContractorListing = subContractorListingService.findById(id);
        byte[] document = subContractorListing.getSubContractorDocument();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("SubContractor-Document.pdf").build());
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }
}