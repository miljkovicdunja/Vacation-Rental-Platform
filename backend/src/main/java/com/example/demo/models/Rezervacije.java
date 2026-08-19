package com.example.demo.models;

import java.sql.Date;

public class Rezervacije {
    private int idR;
    private String turista;
    private int idV;
    private Date datumPocetak;
    private Date datumKraj;
    private int brOdraslih;
    private int brDece;
    private String kartica;
    private String zahtevi;
    private String status;
    private String komentar;

    public Rezervacije(int idR, String turista, int idV, Date datumPocetak, Date datumKraj, int brOdraslih, int brDece,
            String kartica, String zahtevi, String status, String komentar) {
        this.idR = idR;
        this.turista = turista;
        this.idV = idV;
        this.datumPocetak = datumPocetak;
        this.datumKraj = datumKraj;
        this.brOdraslih = brOdraslih;
        this.brDece = brDece;
        this.kartica = kartica;
        this.zahtevi = zahtevi;
        this.status = status;
        this.komentar = komentar;
    }

    public String getKomentar() {
        return komentar;
    }
    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
    public int getIdR() {
        return idR;
    }
    public void setIdR(int idR) {
        this.idR = idR;
    }
    public String getTurista() {
        return turista;
    }
    public void setTurista(String turista) {
        this.turista = turista;
    }
    public int getIdV() {
        return idV;
    }
    public void setIdV(int idV) {
        this.idV = idV;
    }
    public Date getDatumPocetak() {
        return datumPocetak;
    }
    public void setDatumPocetak(Date datumPocetak) {
        this.datumPocetak = datumPocetak;
    }
    public Date getDatumKraj() {
        return datumKraj;
    }
    public void setDatumKraj(Date datumKraj) {
        this.datumKraj = datumKraj;
    }
    public int getBrOdraslih() {
        return brOdraslih;
    }
    public void setBrOdraslih(int brOdraslih) {
        this.brOdraslih = brOdraslih;
    }
    public int getBrDece() {
        return brDece;
    }
    public void setBrDece(int brDece) {
        this.brDece = brDece;
    }
    public String getKartica() {
        return kartica;
    }
    public void setKartica(String kartica) {
        this.kartica = kartica;
    }
    public String getZahtevi() {
        return zahtevi;
    }
    public void setZahtevi(String zahtevi) {
        this.zahtevi = zahtevi;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
