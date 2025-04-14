package com.bryansiegel.ccsdbidsjava.models;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class SubContractorListing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String subContractorCompanyName;
    private String subContractorDocumentId;





    @ManyToOne
    @JoinColumn(name = "bids_id")
    private Bids bids;
    private String subContractorDocumentUrl;

    @Lob
    private byte[] subContractorDocument; // Stores the file's binary data

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getSubContractorDocument() {
        return subContractorDocument;
    }

    public void setSubContractorDocument(byte[] subContractorDocument) {
        this.subContractorDocument = subContractorDocument;
    }

    public String getSubContractorCompanyName() {
        return subContractorCompanyName;
    }

    public void setSubContractorCompanyName(String subContractorCompanyName) {
        this.subContractorCompanyName = subContractorCompanyName;
    }

    public String getSubContractorDocumentId() {
        return subContractorDocumentId;
    }

    public void setSubContractorDocumentId(String subContractorDocumentId) {
        this.subContractorDocumentId = subContractorDocumentId;
    }

    public Bids getBids() {
        return bids;
    }

    public void setBids(Bids bids) {
        this.bids = bids;
    }

    public String getSubContractorDocumentUrl() {
        return subContractorDocumentUrl;
    }

    public void setSubContractorDocumentUrl(String subContractorDocumentUrl) {
        this.subContractorDocumentUrl = subContractorDocumentUrl;
    }

    @Override
    public String toString() {
        return "SubContractorListing{" +
                "id=" + id +
                ", subContractorDocument=" + Arrays.toString(subContractorDocument) +
                ", subContractorCompanyName='" + subContractorCompanyName + '\'' +
                ", subContractorDocumentId='" + subContractorDocumentId + '\'' +
                ", bids=" + bids +
                ", subContractorDocumentUrl='" + subContractorDocumentUrl + '\'' +
                '}';
    }
}