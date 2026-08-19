package com.example.demo.db;

import java.util.List;

import com.example.demo.models.Korisnik;

public interface KorisniciRepoInterface {
    public Korisnik login(Korisnik korisnik);
    public int azurirajKorisnika(Korisnik korisnik);
    public int promeniLozinku(String kor_ime, String novaLozinka);
    public String dohvatiLozinku(String kor_ime);
    public int dodajKorisnika(Korisnik korisnik);
    public int brojTurista();
    public int brojVlasnika();
    public List<Korisnik> sviKorisnici();
    public int obrisiKorisnika(Korisnik korisnik);
    public int deaktivirajKorisnika(Korisnik korisnik);
    public List<Korisnik> korisniciNaCekanju();
    public int aktivirajKorisnika(Korisnik korisnik);
    public int odbijKorisnika(Korisnik korisnik);
    public int azurirajProfilnuSliku(String kor_ime, String profilna_slika);
    public String dohvatiProfilnuSliku(String kor_ime);
    public Korisnik dohvatiKorisnika(String kor_ime);
}
