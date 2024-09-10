package com.rpsa.rpsa.service;

import com.rpsa.rpsa.model.GalleryAlbums;
import com.rpsa.rpsa.model.GalleryPhotos;
import com.rpsa.rpsa.model.Meetings;
import com.rpsa.rpsa.model.Presentations;
import com.rpsa.rpsa.repository.GalleryAlbumsRepository;
import com.rpsa.rpsa.repository.GalleryPhotosRepository;
import com.rpsa.rpsa.repository.MeetingsRespository;
import com.rpsa.rpsa.repository.PresentationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LoadAboutUs {

    @Autowired
    private MeetingsRespository meetingsRespository;

    @Autowired
    private PresentationsRepository presentationRepository;

    @Autowired
    private GalleryAlbumsRepository galleryAlbumsRepository;

    @Autowired
    private GalleryPhotosRepository galleryPhotosRepository;

    public List<Meetings> getMeetings() {
        return meetingsRespository.findAll();
    }



    public Meetings getMeetingById(String meetingid) {
        return meetingsRespository.findById(meetingid).orElse(null);
    }

    public List<Presentations> getPresentations() {
        return presentationRepository.findAll();
    }

    public Presentations getPresentationById(String presentationid) {
        return presentationRepository.findById(presentationid).orElse(null);
    }

    public List<GalleryAlbums> getGalleryAlbums() {
        return galleryAlbumsRepository.findAll();
    }

    public List<GalleryPhotos> getGalleryPhotosByAlbumId(String albumid) {
        return galleryPhotosRepository.findAllById(Collections.singleton(albumid));
    }

    public GalleryAlbums getGalleryPhotosById(String albumid) {
        return galleryAlbumsRepository.findById(albumid).orElse(null);
    }

    public Optional<GalleryAlbums> getAlbumById(String albumid) {
        return galleryAlbumsRepository.findById(albumid);
    }

    public List<GalleryPhotos> getPhotosByAlbum(GalleryAlbums album) {
        return galleryPhotosRepository.findByAlbumid(album);
    }


    public byte[] getPhotoById(String photoid) {
        return galleryPhotosRepository.findById(photoid)
                .map(GalleryPhotos::getPhoto)
                .orElse(null);
    }
}
