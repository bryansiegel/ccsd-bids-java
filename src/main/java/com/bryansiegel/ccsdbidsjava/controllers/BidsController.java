package com.bryansiegel.ccsdbidsjava.controllers;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.models.SubContractorListing;
import com.bryansiegel.ccsdbidsjava.services.BidsService;
import com.bryansiegel.ccsdbidsjava.services.SubContractorListingService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
                            @RequestParam("preBidSignInSheetFile") MultipartFile preBidFile) throws IOException {
        if (!advertisementFile.isEmpty()) {
            bid.setAdvertisementForBids(advertisementFile.getBytes());
            String fileName = bid.getMpidNumber() + "-Ad-for-Bids.pdf";
            bid.setAdvertisementForBidsUrl(fileName);
        }
        if (!preBidFile.isEmpty()) {
            bid.setPreBidSignInSheet(preBidFile.getBytes());
            String preBidFileName = bid.getMpidNumber() + "-Pre-Bid-Sign-In-Sheet.pdf";
            bid.setPreBidSignInSheetUrl(preBidFileName);
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
                          @RequestParam("preBidSignInSheetFile") MultipartFile preBidFile) throws IOException {
        if (!advertisementFile.isEmpty()) {
            bid.setAdvertisementForBids(advertisementFile.getBytes());
            String fileName = bid.getMpidNumber() + "-Ad-for-Bids.pdf";
            bid.setAdvertisementForBidsUrl(fileName);
        }
        if (!preBidFile.isEmpty()) {
            bid.setPreBidSignInSheet(preBidFile.getBytes());
            String preBidFileName = bid.getMpidNumber() + "-Pre-Bid-Sign-In-Sheet.pdf";
            bid.setPreBidSignInSheetUrl(preBidFileName);
        }
        bid.setId(id);
        bidsService.save(bid);
        return "redirect:/admin/bids";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable Long id) {
        bidsService.deleteById(id);
        return "redirect:/admin/bids";
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
    public String createSubContractor(@PathVariable Long bidId, @ModelAttribute SubContractorListing subContractorListing) {
        Bids bid = bidsService.findById(bidId);
        subContractorListing.setBids(bid);
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
    public String editSubContractor(@PathVariable Long bidId, @PathVariable Long id, @ModelAttribute SubContractorListing subContractorListing) {
        subContractorListing.setId(id);
        subContractorListing.setBids(bidsService.findById(bidId));
        subContractorListingService.save(subContractorListing);
        return "redirect:/admin/bids/" + bidId + "/subcontractors";
    }

    @GetMapping("/{bidId}/subcontractors/delete/{id}")
    public String deleteSubContractor(@PathVariable Long bidId, @PathVariable Long id) {
        subContractorListingService.deleteById(id);
        return "redirect:/admin/bids/" + bidId + "/subcontractors";
    }

    //Downloads
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAdvertisementForBids(@PathVariable Long id) {
        Bids bid = bidsService.findById(id);
        byte[] document = bid.getAdvertisementForBids();
        String fileName = bid.getMpidNumber() + "-Ad-for-Bids.pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(fileName).build());
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }

    //PreBid Sign In Sheet Download
    @GetMapping("/download/preBid/{id}")
    public ResponseEntity<byte[]> downloadPreBidSignInSheet(@PathVariable Long id) {
        Bids bid = bidsService.findById(id);
        byte[] document = bid.getPreBidSignInSheet();
        String fileName = bid.getMpidNumber() + "-Pre-Bid-Sign-In-Sheet.pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(fileName).build());
        return new ResponseEntity<>(document, headers, HttpStatus.OK);
    }
}