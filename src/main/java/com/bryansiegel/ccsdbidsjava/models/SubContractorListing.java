package com.bryansiegel.ccsdbidsjava.models;

import jakarta.persistence.*;

@Entity
public class SubContractorListing {

    @Id
    @GeneratedValue( strategy = jakarta.persistence.GenerationType.AUTO)
    private long id;

    private String mpidNumber;
    private String documentId;
    private String documentUrl;

    @ManyToOne
    @JoinColumn(name = "bids_id")
    private Bids bids;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMpidNumber() {
        return mpidNumber;
    }

    public void setMpidNumber(String mpidNumber) {
        this.mpidNumber = mpidNumber;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public Bids getBids() {
        return bids;
    }

    public void setBids(Bids bids) {
        this.bids = bids;
    }

    @Override
    public String toString() {
        return "SubContractorListing{" +
                "id=" + id +
                ", mpidNumber='" + mpidNumber + '\'' +
                ", documentId='" + documentId + '\'' +
                ", documentUrl='" + documentUrl + '\'' +
                ", bids=" + bids +
                '}';
    }
}
