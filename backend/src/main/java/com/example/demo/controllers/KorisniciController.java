package com.example.demo.controllers;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.db.KorisniciRepo;
import com.example.demo.db.VikendiceRepo;
import com.example.demo.models.Korisnik;
import com.example.demo.models.Vikendice;

@RestController
@RequestMapping("/korisnici")
@CrossOrigin(origins = "http://localhost:4200")
public class KorisniciController {
    @PostMapping("/login")
    public Korisnik login(@RequestBody Korisnik korisnik) {
        return new KorisniciRepo().login(korisnik);
    }
    
    @PostMapping(value = "/azuriraj", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)   
    public int azurirajKorisnika(
        @RequestPart("korisnik") Korisnik korisnik,
        @RequestPart(value = "slika", required = false) MultipartFile slika) {
        
        try {
            KorisniciRepo repo = new KorisniciRepo();
            
            // Ako je uploadovana nova profilna slika, snimi je
            if (slika != null && !slika.isEmpty()) {
                // Prvo dohvati staru sliku da bi je obrisao sa diska
                String staraSlika = repo.dohvatiProfilnuSliku(korisnik.getKor_ime());
                
                // Obrisi staru sliku sa diska (ako postoji)
                if (staraSlika != null && !staraSlika.isEmpty()) {
                    try {
                        Path staraPutanja = Paths.get("uploads").resolve(staraSlika);
                        Files.deleteIfExists(staraPutanja);
                    } catch (Exception ignore) {
                        // ignorisi gresku brisanja
                    }
                }
                
                // Snimi novu sliku
                String novoIme = UUID.randomUUID() + "_" + slika.getOriginalFilename();
                Path putanja = Paths.get("uploads").resolve(novoIme);
                Files.createDirectories(putanja.getParent());
                Files.copy(slika.getInputStream(), putanja, StandardCopyOption.REPLACE_EXISTING);
                
                // VAZNO: Postavi novo ime slike u objekat
                korisnik.setProfilna_slika(novoIme);
                System.out.println("Nova slika postavljena: " + novoIme);
            } else {
                // Ako nije uploadovana nova slika, ucitaj postojecu iz baze
                String postojecaSlika = repo.dohvatiProfilnuSliku(korisnik.getKor_ime());
                if (postojecaSlika != null && !postojecaSlika.isEmpty()) {
                    korisnik.setProfilna_slika(postojecaSlika);
                }
            }
            
            System.out.println("Azuriram korisnika " + korisnik.getKor_ime() + " sa slikom: " + korisnik.getProfilna_slika());
            int rezultat = repo.azurirajKorisnika(korisnik);
            System.out.println("Rezultat azuriranja: " + rezultat);
            return rezultat;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @PostMapping("/promenaLozinke/{kor_ime}")
    public int promenaLozinke(@PathVariable String kor_ime,
                           @RequestParam String novaLozinka) {
    return new KorisniciRepo().promeniLozinku(kor_ime, novaLozinka);
}
    @GetMapping("/dohvatiLozinku/{kor_ime}")
    public String dohvatiLozinku(@PathVariable String kor_ime) {
        return new KorisniciRepo().dohvatiLozinku(kor_ime);
    }
    @GetMapping("/dohvatiKorisnika/{kor_ime}")
    public Korisnik dohvatiKorisnika(@PathVariable String kor_ime) {
        return new KorisniciRepo().dohvatiKorisnika(kor_ime);
    }
    @PostMapping("/dodajKorisnika")
    public int dodajKorisnika(@RequestBody Korisnik korisnik) {
        return new KorisniciRepo().dodajKorisnika(korisnik);
    }
    
    @PostMapping(value = "/dodajKorisnikaMultipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int dodajKorisnikaMultipart(
        @RequestPart("korisnik") Korisnik korisnik,
        @RequestPart(value = "slika", required = false) MultipartFile slika) {
        
        try {
            // If a profile photo is uploaded, save it
            if (slika != null && !slika.isEmpty()) {
                String ime = UUID.randomUUID() + "_" + slika.getOriginalFilename();
                Path putanja = Paths.get("uploads").resolve(ime);
                Files.createDirectories(putanja.getParent());
                Files.copy(slika.getInputStream(), putanja, StandardCopyOption.REPLACE_EXISTING);
                korisnik.setProfilna_slika(ime);
            }
            
            return new KorisniciRepo().dodajKorisnika(korisnik);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @GetMapping("/brojTurista")
    public int brojTurista() {
        return new KorisniciRepo().brojTurista();
    }
    @GetMapping("/brojVlasnika")
    public int brojVlasnika() {
        return new KorisniciRepo().brojVlasnika();
    }
    @GetMapping("/sviKorisnici")
    public List<Korisnik> sviKorisnici() {
        return new KorisniciRepo().sviKorisnici();
    }
    @PostMapping("/obrisiKorisnika")    
    public int obrisiKorisnika(@RequestBody Korisnik korisnik) {
        try {
            // Dohvati sve vikendice ovog korisnika
            VikendiceRepo vikendiceRepo = new VikendiceRepo();
            List<Vikendice> vikendice = vikendiceRepo.vikendiceVlasnika(korisnik.getKor_ime());
            
            // Obrisi sve slike sa diska za svaku vikendicu
            if (vikendice != null) {
                for (Vikendice vikendica : vikendice) {
                    String slike = vikendica.getSlike();
                    if (slike != null && !slike.isEmpty()) {
                        String[] imena = slike.split(",");
                        for (String ime : imena) {
                            try {
                                Path putanja = Paths.get("uploads").resolve(ime.trim());
                                Files.deleteIfExists(putanja);
                            } catch (Exception ignore) {
                                // ignorisi gresku brisanja sa diska
                            }
                        }
                    }
                }
            }
            
            // Obrisi korisnika (i njegove vikendice iz baze)
            return new KorisniciRepo().obrisiKorisnika(korisnik);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @PostMapping("/deaktivirajKorisnika")    
    public int deaktivirajKorisnika(@RequestBody Korisnik korisnik) {
        return new KorisniciRepo().deaktivirajKorisnika(korisnik);
    }
    @GetMapping("/korisniciNaCekanju")
    public List<Korisnik> korisniciNaCekanju() {
        return new KorisniciRepo().korisniciNaCekanju();
    }
    @PostMapping("/prihvatiKorisnika")
    public int prihvatiKorisnika(@RequestBody Korisnik korisnik) {
        return new KorisniciRepo().aktivirajKorisnika(korisnik);
    }
    @PostMapping("/odbijKorisnika")
    public int odbijKorisnika(@RequestBody Korisnik korisnik) {
        return new KorisniciRepo().odbijKorisnika(korisnik);
    }
    
    @PostMapping(value = "/uploadProfilnaSlika", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfilnaSlika(
        @RequestPart("kor_ime") String kor_ime,
        @RequestPart("slika") MultipartFile slika) {
        
        try {
            if (slika == null || slika.isEmpty()) {
                return ResponseEntity.badRequest().body("Greška: slika nije uploadovana.");
            }
            
            // Generisi jedinstveno ime fajla
            String ime = UUID.randomUUID() + "_" + slika.getOriginalFilename();
            Path putanja = Paths.get("uploads").resolve(ime);
            Files.createDirectories(putanja.getParent());
            Files.copy(slika.getInputStream(), putanja, StandardCopyOption.REPLACE_EXISTING);
            
            // Azuriraj bazu sa novim imenom slike
            KorisniciRepo repo = new KorisniciRepo();
            int rezultat = repo.azurirajProfilnuSliku(kor_ime, ime);
            
            if (rezultat > 0) {
                return ResponseEntity.ok(ime); // vrati ime fajla
            } else {
                return ResponseEntity.status(500).body("Greška pri ažuriranju baze.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greška: " + e.getMessage());
        }
    }
    
    @GetMapping("/profilnaSlika/{kor_ime}")
    public ResponseEntity<String> getProfilnaSlika(@PathVariable String kor_ime) {
        try {
            KorisniciRepo repo = new KorisniciRepo();
            String slika = repo.dohvatiProfilnuSliku(kor_ime);
            
            if (slika == null || slika.isEmpty()) {
                return ResponseEntity.ok(""); // nema profilne slike
            }
            
            return ResponseEntity.ok("/uploads/" + slika);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }
}