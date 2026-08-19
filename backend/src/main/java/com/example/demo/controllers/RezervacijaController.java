package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.RezervacijaRepo;
import com.example.demo.models.Korisnik;
import com.example.demo.models.Rezervacije;

@RestController
@RequestMapping("/rezervacije")
@CrossOrigin(origins = "http://localhost:4200")
public class RezervacijaController{
    @GetMapping("/24h")
    public int brojRezervacija24h(){
        return new RezervacijaRepo().brojRezervacija24h();
    } 
    @GetMapping("/7dana")
    public int brojRezervacija7dana(){  
        return new RezervacijaRepo().brojRezervacija7dana();
    }
    @GetMapping("/30dana")
    public int brojRezervacija30dana(){  
        return new RezervacijaRepo().brojRezervacija30dana();
    }   
    @GetMapping("/trenutne/{korisnik}")
    public List<Rezervacije> trenutneRezervacijeKorisnika(@PathVariable String korisnik){  
        return new RezervacijaRepo().trenutneRezervacijeKorisnika(korisnik);
    }
    @PostMapping("/dodaj")
    public int dodajRezervaciju(@RequestBody Rezervacije rezervacija){  
        return new RezervacijaRepo().dodajRezervaciju(rezervacija);
    }
    @PostMapping("/neobradjene")
    public List<Rezervacije> neobradjeneRezervacije(@RequestBody Korisnik korisnik){  
        return new RezervacijaRepo().neobradjeneRezervacije(korisnik);
    }
    @PostMapping("/prihvati/{rezervacija}")
    public int prihvatiRezervaciju(@PathVariable int rezervacija){  
        return new RezervacijaRepo().prihvatiRezervaciju(rezervacija);
    }
    @PostMapping("/odbij/{rezervacija}/{komentar}")
    public int odbijRezervaciju(@PathVariable int rezervacija,@PathVariable String komentar){  
        return new RezervacijaRepo().odbijRezervaciju(rezervacija, komentar);
    }
    @PostMapping("/otkazi")
    public int otkaziRezervaciju(@RequestBody Rezervacije rezervacija){  
        return new RezervacijaRepo().otkaziRezervaciju(rezervacija);
    }   
}