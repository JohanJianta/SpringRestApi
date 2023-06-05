package com.wordle.wordlemania.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.Guest;
import com.wordle.wordlemania.Repos.GuestRepositories;

@Service
public class GuestService {
    
    @Autowired
    private GuestRepositories guestRepository;

    public Optional<Guest> getGuestById (int guestId) {
        return guestRepository.findById(guestId);
    }

    public Guest saveGuest (String name) {
        Guest guest = new Guest();
        guest.setName(name);
        return guestRepository.save(guest);
    }
}
