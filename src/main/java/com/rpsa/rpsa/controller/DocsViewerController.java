package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.Notification;
import com.rpsa.rpsa.model.T_Appeals;
import com.rpsa.rpsa.repository.TAppealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class DocsViewerController {

    @Autowired
    private TAppealsRepository appealsRepo;

    @GetMapping("/viewsupportdoc/{appealcode}")
    public ResponseEntity<byte[]> viewSupportDoc(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getSupportdoc() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "supportdoc.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getSupportdoc(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewsupportdoc2/{appealcode}")
    public ResponseEntity<byte[]> viewSupportDoc2(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getSupportdoc2() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "supportdoc2.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getSupportdoc2(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewsupportdoc3/{appealcode}")
    public ResponseEntity<byte[]> viewSupportDoc3(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getSupportdoc3() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "supportdoc3.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getSupportdoc3(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewsupportdoc4/{appealcode}")
    public ResponseEntity<byte[]> viewSupportDoc4(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getSupportdoc4() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "supportdoc4.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getSupportdoc4(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewsupportdoc5/{appealcode}")
    public ResponseEntity<byte[]> viewSupportDoc5(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getSupportdoc5() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "supportdoc5.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getSupportdoc5(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewordercopy/{appealcode}")
    public ResponseEntity<byte[]> viewOrderCopy(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getOrdercopy() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "ordercopy.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getOrdercopy(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewidproof/{appealcode}")
    public ResponseEntity<byte[]> viewIdProof(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getIdproof() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "idproof.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getIdproof(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewform1/{appealcode}")
    public ResponseEntity<byte[]> viewForm1(@PathVariable String appealcode) {
        T_Appeals appeal = appealsRepo.findById(appealcode).orElse(null);

        if (appeal != null && appeal.getForm1doc() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type to PDF
            headers.setContentDispositionFormData("inline", "form1doc.pdf");  // Changed from "attachment" to "inline"
            return new ResponseEntity<>(appeal.getForm1doc(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
