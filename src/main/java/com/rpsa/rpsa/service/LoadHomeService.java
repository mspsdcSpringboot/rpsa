package com.rpsa.rpsa.service;


import com.rpsa.rpsa.dto.AppealToAppellateDTO;
import com.rpsa.rpsa.dto.ApplealsToCommissionerDTO;
import com.rpsa.rpsa.dto.GalleryPhotoDTO;
import com.rpsa.rpsa.dto.SubmissionDetailsDto;
import com.rpsa.rpsa.model.*;
import com.rpsa.rpsa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoadHomeService {

    @Autowired
    private VisitorsRepository visitorsRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ContactsRepository contactsRepository;
    @Autowired
    private GalleryHomePageRepository galleryHomePageRepository;
    @Autowired
    private WhatsnewRepository whatsNewRepository;

    @Autowired
    private InitiatedDataRepository initiatedDataRepository;

    @Autowired
    private TAppealsRepository tAppealsRepository;


    //Getting visitor's count
    public String getVisitorsCount(Visitors visitors){
        Optional<Visitors> getVisitor = visitorsRepository.findById("1");
        return getVisitor.isPresent() ? getVisitor.get().getTotal() : "No visitors found";
    };


    //Getting total services
    public Integer getTotalServices(){
        return serviceRepository.countTotalServiceNames();
    }


    public List<Contacts> getContacts() {
        return contactsRepository.findAll();
    }

    public List<GalleryPhotoDTO> getHomePageGallery() {
        List<GalleryHomePage> photos = galleryHomePageRepository.findAll();
        return photos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private GalleryPhotoDTO convertToDTO(GalleryHomePage photo) {
        String base64Image = Base64.getEncoder().encodeToString(photo.getPhoto());
        return new GalleryPhotoDTO(photo.getPhotoid(), base64Image);
    }

    public List<WhatsNew> getNoticeBoardData() {
        return whatsNewRepository.findAll();
    }



    public SubmissionDetailsDto getOverallInfo() {

        List<InitiatedData> submissions=  initiatedDataRepository.findAll();

        SubmissionDetailsDto submissionDetailsDto = new SubmissionDetailsDto();



        submissionDetailsDto.setTotalSubmitted(submissions.size());
        submissionDetailsDto.setTotalDelivered(submissions.stream()
                .filter(data -> data.getTasktypeid() != null && "4".equals(data.getTasktypeid().getTasktypeid()))
                .count());
        submissionDetailsDto.setTotalRejected(submissions.stream()
                .filter(data -> data.getTasktypeid() != null && "5".equals(data.getTasktypeid().getTasktypeid()))
                .count());
        submissionDetailsDto.setWithApplicant(submissions.stream()
                .filter(data -> data.getTasktypeid() != null && "2".equals(data.getTasktypeid().getTasktypeid()))
                .count());
        submissionDetailsDto.setWithOfficialWithinSLA(submissions.stream()
                .filter(data -> data.getDaystaken() != null && isInteger(data.getDaystaken())
                        && Integer.parseInt(data.getDaystaken()) >= 0
                        && data.getCheckflag() != null && !List.of("4", "5").contains(data.getCheckflag())
                        && List.of("0", "1", "3").contains(data.getCheckflag()))
                .count());
        submissionDetailsDto.setWithOfficialBeyondSLA(submissions.stream()
                .filter(data -> data.getDaystaken() != null && isInteger(data.getDaystaken())
                        && Integer.parseInt(data.getDaystaken()) < 0
                        && data.getCheckflag() != null && !List.of("4", "5").contains(data.getCheckflag())
                        && List.of("0", "1", "3").contains(data.getCheckflag()))
                .count());
        submissionDetailsDto.setTotalPending(submissions.stream()
                .filter(data -> data.getTasktypeid() != null && List.of("0", "1", "2", "3").contains(data.getTasktypeid().getTasktypeid()))
                .count());


        return submissionDetailsDto;
    }

    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public AppealToAppellateDTO getAppealToAppellateDetails() {
        List<T_Appeals> appealToAppellate = tAppealsRepository.findAll();
        AppealToAppellateDTO appealToAppellateDto = new AppealToAppellateDTO();


        appealToAppellateDto.setAppealCount(appealToAppellate.size());


        appealToAppellateDto.setSubmitted(appealToAppellate.stream()
                .filter(data -> data.getAppeallevel() != null && "1".equals(data.getAppeallevel()))
                .count());

        appealToAppellateDto.setDisposed(appealToAppellate.stream().filter(data -> "5".equals(data.getStatusid().getStatusid()) && "1".equals(data.getAppeallevel())).count());

        appealToAppellateDto.setRejected(appealToAppellate.stream().filter(data -> "3".equals(data.getStatusid().getStatusid()) && "1".equals(data.getAppeallevel())).count());

        appealToAppellateDto.setPendingWithin(appealToAppellate.stream().filter(data ->
                !"5".equals(data.getStatusid().getStatusid()) &&
                        !"3".equals(data.getStatusid().getStatusid()) &&
                        "1".equals(data.getAppeallevel()) &&
                        isInteger(data.getDaysleft()) &&
                        Integer.parseInt(data.getDaysleft()) >= 0).count());



        appealToAppellateDto.setPendingBeyond(appealToAppellate.stream().filter(data ->
                !"5".equals(data.getStatusid().getStatusid()) &&
                        !"3".equals(data.getStatusid().getStatusid()) &&
                        "1".equals(data.getAppeallevel()) &&
                        isInteger(data.getDaysleft()) &&
                        Integer.parseInt(data.getDaysleft()) < 0).count());


        appealToAppellateDto.setPenalty(appealToAppellate.stream().filter(data -> "8".equals(data.getStatusid().getStatusid())).count());


        System.out.println(appealToAppellateDto);
        return appealToAppellateDto;




    }

    public ApplealsToCommissionerDTO getAppealToCommissionerDetails() {
        List<T_Appeals> appealToCommissioner = tAppealsRepository.findAll();
        ApplealsToCommissionerDTO commissionerAppeals = new ApplealsToCommissionerDTO();

        commissionerAppeals.setAppealCount(appealToCommissioner.size());
        commissionerAppeals.setSubmitted(appealToCommissioner.stream().filter(a -> "2".equals(a.getAppeallevel())).count());
        commissionerAppeals.setDisposed(appealToCommissioner.stream().filter(a -> "5".equals(a.getStatusid().getStatusid()) && "2".equals(a.getAppeallevel())).count());
        commissionerAppeals.setRejected(appealToCommissioner.stream().filter(a -> "3".equals(a.getStatusid().getStatusid()) && "2".equals(a.getAppeallevel())).count());
        commissionerAppeals.setPendingWithin(appealToCommissioner.stream().filter(a ->
                !"5".equals(a.getStatusid().getStatusid()) &&
                        !"3".equals(a.getStatusid().getStatusid()) &&
                        "2".equals(a.getAppeallevel()) &&
                        isInteger(a.getDaysleft()) &&
                        Integer.parseInt(a.getDaysleft()) >= 0).count());
        commissionerAppeals.setPendingBeyond(appealToCommissioner.stream().filter(a ->
                !"5".equals(a.getStatusid().getStatusid()) &&
                        !"3".equals(a.getStatusid().getStatusid()) &&
                        "2".equals(a.getAppeallevel()) &&
                        isInteger(a.getDaysleft()) &&
                        Integer.parseInt(a.getDaysleft()) < 0).count());
        commissionerAppeals.setPenalty(appealToCommissioner.stream().filter(a -> "8".equals(a.getStatusid().getStatusid())).count());

        System.out.println(commissionerAppeals);
        return commissionerAppeals;


    }
}
