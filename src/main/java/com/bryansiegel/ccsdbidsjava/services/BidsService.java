package com.bryansiegel.ccsdbidsjava.services;

import com.bryansiegel.ccsdbidsjava.models.Bids;
import com.bryansiegel.ccsdbidsjava.repositories.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidsService {

    @Autowired
    private BidsRepository bidsRepository;

    public List<Bids> findAll() {
        return bidsRepository.findAll();
    }

    public Bids findById(Long id) {
        return bidsRepository.findById(id).orElse(null);
    }

    public void save(Bids bid) {
        bidsRepository.save(bid);
    }

    public void deleteById(Long id) {
        bidsRepository.deleteById(id);
    }
}