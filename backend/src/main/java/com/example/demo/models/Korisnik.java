package com.example.demo.models;

public class Korisnik{
    private String kor_ime;
    private String lozinka;
    private String ime;
    private String prezime;
    private String pol;
    private String adresa;
    private String mejl;
    private String tip;
    private String kontakt_telefon;
    private String broj_kreditne_kartice;
    private String profilna_slika;
    private String status;
    //empty constructor
    public Korisnik() {
    }

    

    public Korisnik(String kor_ime, String lozinka, String ime, String prezime, String pol,String adresa, String mejl, String tip,
            String kontakt_telefon, String broj_kreditne_kartice, String profilna_slika, String status) {
        this.kor_ime = kor_ime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.pol = pol;
        this.adresa = adresa;
        this.mejl = mejl;
        this.tip = tip;
        this.kontakt_telefon = kontakt_telefon;
        this.broj_kreditne_kartice = broj_kreditne_kartice;
        this.profilna_slika = profilna_slika;
        this.status = status;
    }
    public String getAdresa() {
        return adresa;
    }
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getKor_ime() {
        return kor_ime;
    }
    public void setKor_ime(String kor_ime) {
        this.kor_ime = kor_ime;
    }
    public String getLozinka() {
        return lozinka;
    }
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getPol() {
        return pol;
    }
    public void setPol(String pol) {
        this.pol = pol;
    }
    public String getMejl() {
        return mejl;
    }
    public void setMejl(String mejl) {
        this.mejl = mejl;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public String getKontakt_telefon() {
        return kontakt_telefon;
    }
    public void setKontakt_telefon(String kontakt_telefon) {
        this.kontakt_telefon = kontakt_telefon;
    }
    public String getBroj_kreditne_kartice() {
        return broj_kreditne_kartice;
    }
    public void setBroj_kreditne_kartice(String broj_kreditne_kartice) {
        this.broj_kreditne_kartice = broj_kreditne_kartice;
    }

    public String getProfilna_slika() {
        return profilna_slika;
    }       
    public void setProfilna_slika(String profilna_slika) {
        this.profilna_slika = profilna_slika;
    }
    
}