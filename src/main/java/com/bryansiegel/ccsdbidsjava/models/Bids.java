package com.bryansiegel.ccsdbidsjava.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Bids {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contractName;
    private String advertisementForBids;
    private String mpidNumber;
    private String documentUrl;

    private String preBidSignInSheet;
    private String bidTabulationSheet;
    private boolean isActive = true;

    @OneToMany(mappedBy = "bids", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubContractorListing> subContractorListings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getAdvertisementForBids() {
        return advertisementForBids;
    }

    public void setAdvertisementForBids(String advertisementForBids) {
        this.advertisementForBids = advertisementForBids;
    }

    public String getMpidNumber() {
        return mpidNumber;
    }

    public void setMpidNumber(String mpidNumber) {
        this.mpidNumber = mpidNumber;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getPreBidSignInSheet() {
        return preBidSignInSheet;
    }

    public void setPreBidSignInSheet(String preBidSignInSheet) {
        this.preBidSignInSheet = preBidSignInSheet;
    }

    public String getBidTabulationSheet() {
        return bidTabulationSheet;
    }

    public void setBidTabulationSheet(String bidTabulationSheet) {
        this.bidTabulationSheet = bidTabulationSheet;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<SubContractorListing> getSubContractorListings() {
        return subContractorListings;
    }

    public void setSubContractorListings(List<SubContractorListing> subContractorListings) {
        this.subContractorListings = subContractorListings;
    }

    @Override
    public String toString() {
        return "Bids{" +
                "id=" + id +
                ", contractName='" + contractName + '\'' +
                ", advertisementForBids='" + advertisementForBids + '\'' +
                ", mpidNumber='" + mpidNumber + '\'' +
                ", documentUrl='" + documentUrl + '\'' +
                ", preBidSignInSheet='" + preBidSignInSheet + '\'' +
                ", bidTabulationSheet='" + bidTabulationSheet + '\'' +
                ", isActive=" + isActive +
                ", subContractorListings=" + subContractorListings +
                '}';
    }
}
