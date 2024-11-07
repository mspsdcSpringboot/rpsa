package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.WhatsNew;
import com.rpsa.rpsa.repository.WhatsnewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WhatsNewService {

    @Autowired
    private WhatsnewRepository wNewRepo;
    public WhatsNew addNewWhatsNew(WhatsNew whatsnew) {
        WhatsNew  newWhatsNew = new WhatsNew();

        Integer whatsNewId = wNewRepo.findMaxId();

        if (whatsNewId == null) {
            whatsNewId = 0; // Starting id if no records exist
        }

        int newWhatsNewIdMaxId = whatsNewId + 1;
        String stringMaxId = String.valueOf(newWhatsNewIdMaxId);
        newWhatsNew.setWhatsnewid(stringMaxId);

        newWhatsNew.setHeading(whatsnew.getHeading());
        newWhatsNew.setNewbody(whatsnew.getNewbody());
        newWhatsNew.setWhatsnewdate(whatsnew.getWhatsnewdate());
        wNewRepo.save(newWhatsNew);
        return newWhatsNew;
    }

    public WhatsNew updateWhatsNew(WhatsNew whatsnew) {
        WhatsNew existingWhatsNew = wNewRepo.findById(whatsnew.getWhatsnewid()).orElseThrow(() -> new EntityNotFoundException("WhatsNew entry not found with id: " + whatsnew.getWhatsnewid()));
        existingWhatsNew.setHeading(whatsnew.getHeading());
        existingWhatsNew.setNewbody(whatsnew.getNewbody());
        existingWhatsNew.setWhatsnewdate(whatsnew.getWhatsnewdate());
        wNewRepo.save(existingWhatsNew);
        return existingWhatsNew;
    }
}
