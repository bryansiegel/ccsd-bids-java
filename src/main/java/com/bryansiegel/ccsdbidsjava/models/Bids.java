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

    @Lob
    private byte[] preBidSignInSheet;
    private String preBidSignInSheetUrl;


    @Lob
    private byte[] bidTabulationSheet;
    private String bidTabulationSheetUrl;

    private boolean isActive = true;

    //SubContractor Listings
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

    public byte[] getBidTabulationSheet() {
        return bidTabulationSheet;
    }

    public void setBidTabulationSheet(byte[] bidTabulationSheet) {
        this.bidTabulationSheet = bidTabulationSheet;
    }

    public String getBidTabulationSheetUrl() {
        return bidTabulationSheetUrl;
    }

    public void setBidTabulationSheetUrl(String bidTabulationSheetUrl) {
        this.bidTabulationSheetUrl = bidTabulationSheetUrl;
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
                ", preBidSignInSheet=" + Arrays.toString(preBidSignInSheet) +
                ", preBidSignInSheetUrl='" + preBidSignInSheetUrl + '\'' +
                ", bidTabulationSheet=" + Arrays.toString(bidTabulationSheet) +
                ", bidTabulationSheetUrl='" + bidTabulationSheetUrl + '\'' +
                ", isActive=" + isActive +
                ", subContractorListings=" + subContractorListings +
                '}';
    }
}
