package com.example.demo.models;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Vikendice {
    private int idV;
    private String naziv;
    private String mesto;
    private String usluge;
    private Float ocena;
    private int cenovnikProlece;
    private int cenovnikLeto;
    private int cenovnikJesen;
    private int cenovnikZima;
    private String telefon;
    private String koordinate;
    private String slike;
    private String vlasnik;
    private String status;

    
    private List<MultipartFile> uploadSlike;
    public List<MultipartFile> getUploadSlike() {
        return uploadSlike;
    }
    public void setUploadSlike(List<MultipartFile> uploadSlike) {
        this.uploadSlike = uploadSlike;
    }

    public Vikendice() {}

    public Vikendice(int idV, String naziv, String mesto, String usluge,Float ocena, int cenovnikProlece, int cenovnikLeto,
            int cenovnikJesen, int cenovnikZima, String telefon, String koordinate, String slike, String vlasnik,
            String status) {
        this.idV = idV;
        this.naziv = naziv;
        this.mesto = mesto;
        this.usluge = usluge;
        this.ocena=ocena;
        this.cenovnikProlece = cenovnikProlece;
        this.cenovnikLeto = cenovnikLeto;
        this.cenovnikJesen = cenovnikJesen;
        this.cenovnikZima = cenovnikZima;
        this.telefon = telefon;
        this.koordinate = koordinate;
        this.slike = slike;
        this.vlasnik = vlasnik;
        this.status = status;
    }
    public Float getOcena() {
        return ocena;
    }
    public void setOcena(Float ocena) {
        this.ocena = ocena;
    }
    public int getIdV() {
        return idV;
    }
    public void setIdV(int idV) {
        this.idV = idV;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getMesto() {
        return mesto;
    }
    public void setMesto(String mesto) {
        this.mesto = mesto;
    }
    public String getUsluge() {
        return usluge;
    }
    public void setUsluge(String usluge) {
        this.usluge = usluge;
    }
    public int getCenovnikProlece() {
        return cenovnikProlece;
    }
    public void setCenovnikProlece(int cenovnikProlece) {
        this.cenovnikProlece = cenovnikProlece;
    }
    public int getCenovnikLeto() {
        return cenovnikLeto;
    }
    public void setCenovnikLeto(int cenovnikLeto) {
        this.cenovnikLeto = cenovnikLeto;
    }
    public int getCenovnikJesen() {
        return cenovnikJesen;
    }
    public void setCenovnikJesen(int cenovnikJesen) {
        this.cenovnikJesen = cenovnikJesen;
    }
    public int getCenovnikZima() {
        return cenovnikZima;
    }
    public void setCenovnikZima(int cenovnikZima) {
        this.cenovnikZima = cenovnikZima;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public String getKoordinate() {
        return koordinate;
    }
    public void setKoordinate(String koordinate) {
        this.koordinate = koordinate;
    }
    public String getSlike() {
        return slike;
    }
    public void setSlike(String slike) {
        this.slike = slike;
    }
    public String getVlasnik() {
        return vlasnik;
    }
    public void setVlasnik(String vlasnik) {
        this.vlasnik = vlasnik;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
