package com.bryansiegel.ccsdbidsjava.controllers;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.services.BidsService;
import com.bryansiegel.ccsdbidsjava.services.SubContractorListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicController {

    private final BidsService bidsService;
    private final SubContractorListingService subContractorListingService;

    public PublicController(BidsService bidsService, SubContractorListingService subContractorListingService) {
        this.bidsService = bidsService;
        this.subContractorListingService = subContractorListingService;
    }

    @GetMapping("/")
    public String getBids(Model model) {
        List<Bids> bids = bidsService.findAll(); // Fetch bids from the service
        model.addAttribute("bids", bids);
        return "public/index";
    }
}
