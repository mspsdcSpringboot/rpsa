package com.rpsa.rpsa.controller;
import com.rpsa.rpsa.model.GalleryAlbums;
import com.rpsa.rpsa.model.GalleryPhotos;
import com.rpsa.rpsa.model.Meetings;
import com.rpsa.rpsa.model.Presentations;
import com.rpsa.rpsa.service.LoadAboutUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/public")
public class AboutUsController {

    @Autowired
    private LoadAboutUs loadAboutUs;

    //Commission Load
    @GetMapping("/about_us")
    public String getCommissionPage(){
        return "/pages/about_us/commission";
    }

    //Functions & Duties load
    @GetMapping("/functions")
    public String getFunctionsPage(){
        return "/pages/about_us/functions";
    }

    //Review meetings Load
    @GetMapping("/reviewmeetings")
    public String getReviewMeetingsPage(Model model){

        List<Meetings> meetings = loadAboutUs.getMeetings();
        model.addAttribute("meetings", meetings);
        return "/pages/about_us/reviewmeetings";
    }


    @GetMapping("/viewmeetingdoc/{meetingid}")
    public ResponseEntity<byte[]> viewMeetingDoc(@PathVariable String meetingid) {
        Meetings meeting = loadAboutUs.getMeetingById(meetingid);
        if (meeting != null && meeting.getDoc() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDispositionFormData("attachment", "review_meeting.pdf");
            return new ResponseEntity<>(meeting.getDoc(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //Load Presentation
    @GetMapping("/presentations")
    public String getPresentationsPage(Model model){
        List<Presentations> presentations = loadAboutUs.getPresentations();
        model.addAttribute("presentations", presentations);
        return "/pages/about_us/presentations";
    }

    @GetMapping("/viewpresentationdoc/{presentationid}")
    public ResponseEntity<byte[]> viewPresentationDoc(@PathVariable String presentationid) {
       Presentations presentations = loadAboutUs.getPresentationById(presentationid);
        if (presentations != null && presentations.getDoc() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDispositionFormData("attachment", "presentation.pdf");
            return new ResponseEntity<>(presentations.getDoc(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //Load Albums
    @GetMapping("/albums")
    public String getAlbumsPage(Model model, @RequestParam(required = false) String albumid){
        List<GalleryAlbums> albums = loadAboutUs.getGalleryAlbums();
        model.addAttribute("albums", albums);


        if (albumid != null) {
            Optional<GalleryAlbums> albumOpt = loadAboutUs.getAlbumById(albumid);
            if (albumOpt.isPresent()) {
                GalleryAlbums album = albumOpt.get();
                List<GalleryPhotos> photos = loadAboutUs.getPhotosByAlbum(album);
                model.addAttribute("selectedAlbum", album);
                model.addAttribute("albumPhotos", photos);
            }
        }

        return "/pages/about_us/albums";
    }

    @GetMapping("/albums/thumbnail/{albumid}")
    public ResponseEntity<byte[]> viewThumbnail(@PathVariable String albumid) {

        List<GalleryPhotos> photos = loadAboutUs.getGalleryPhotosByAlbumId(albumid);
        GalleryAlbums album = loadAboutUs.getGalleryPhotosById(albumid);
        if (album != null ) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or MediaType.IMAGE_PNG
            return new ResponseEntity<>(album.getThumbnail(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/photos/{photoid}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String photoid) {
        byte[] photo = loadAboutUs.getPhotoById(photoid);

        if (photo != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(photo, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}
