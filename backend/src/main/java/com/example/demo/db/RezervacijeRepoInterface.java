package com.example.demo.db;

import java.util.List;

import com.example.demo.models.Korisnik;
import com.example.demo.models.Rezervacije;

public interface RezervacijeRepoInterface {
    public int brojRezervacija24h();
    public int brojRezervacija7dana();
    public int brojRezervacija30dana();
    public List<Rezervacije> trenutneRezervacijeKorisnika(String korisnik);
    public int dodajRezervaciju(Rezervacije rezervacija);
    public List<Rezervacije> neobradjeneRezervacije(Korisnik korisnik);
    public int prihvatiRezervaciju(int rezervacija);
    public int odbijRezervaciju(int rezervacija, String komentar);
    public int otkaziRezervaciju(Rezervacije rezervacija);
}
