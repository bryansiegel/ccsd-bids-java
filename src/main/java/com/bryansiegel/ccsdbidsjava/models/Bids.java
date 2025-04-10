package com.bryansiegel.ccsdbidsjava.models;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
public class Bids {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contractName;
    @Lob
    private byte[] advertisementForBids;
    private String advertisementForBidsUrl;

    private String mpidNumber;
    private String documentUrl;

    @Lob
    private byte[] preBidSignInSheet;
    private String preBidSignInSheetUrl;


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

    public byte[] getAdvertisementForBids() {
        return advertisementForBids;
    }

    public void setAdvertisementForBids(byte[] advertisementForBids) {
        this.advertisementForBids = advertisementForBids;
    }

    public String getAdvertisementForBidsUrl() {
        return advertisementForBidsUrl;
    }

    public void setAdvertisementForBidsUrl(String advertisementForBidsUrl) {
        this.advertisementForBidsUrl = advertisementForBidsUrl;
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

    public byte[] getPreBidSignInSheet() {
        return preBidSignInSheet;
    }

    public void setPreBidSignInSheet(byte[] preBidSignInSheet) {
        this.preBidSignInSheet = preBidSignInSheet;
    }

    public String getPreBidSignInSheetUrl() {
        return preBidSignInSheetUrl;
    }

    public void setPreBidSignInSheetUrl(String preBidSignInSheetUrl) {
        this.preBidSignInSheetUrl = preBidSignInSheetUrl;
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
                ", advertisementForBids=" + Arrays.toString(advertisementForBids) +
                ", advertisementForBidsUrl='" + advertisementForBidsUrl + '\'' +
                ", mpidNumber='" + mpidNumber + '\'' +
                ", documentUrl='" + documentUrl + '\'' +
                ", preBidSignInSheet=" + Arrays.toString(preBidSignInSheet) +
                ", preBidSignInSheetUrl='" + preBidSignInSheetUrl + '\'' +
                ", bidTabulationSheet='" + bidTabulationSheet + '\'' +
                ", isActive=" + isActive +
                ", subContractorListings=" + subContractorListings +
                '}';
    }
}
