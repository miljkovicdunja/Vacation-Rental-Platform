package com.example.demo.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.db.VikendiceRepo;
import com.example.demo.models.Vikendice;

@RestController
@RequestMapping("/vikendice")
@CrossOrigin(origins = "http://localhost:4200")
public class VikendiceController {
    @GetMapping("/brojVikendica")
    public int brojVikendica() {
        return new VikendiceRepo().brojVikendica();
    }
    @GetMapping("/sveVikendice")
    public List<Vikendice> sveVikendice() {
        return new VikendiceRepo().sveVikendice();
    }
    @GetMapping("/sortiraneOpadajuce")
    public List<Vikendice> sortiraneVikendiceOpadajuce() {
        return new VikendiceRepo().sortiraneVikendiceOpadajuce();
    }
    @GetMapping("/sortiraneRastuce")
    public List<Vikendice> sortiraneVikendiceRastuce() {
        return new VikendiceRepo().sortiraneVikendiceRastuce();
    }
    @GetMapping("/sortiraneMestoOpadajuce")
    public List<Vikendice> sortiraneVikendiceMestoOpadajuce() {
        return new VikendiceRepo().sortiraneVikendiceMestoOpadajuce();
    }   
    @GetMapping("/sortiraneMestoRastuce")
    public List<Vikendice> sortiraneVikendiceMestoRastuce() {
        return new VikendiceRepo().sortiraneVikendiceMestoRastuce();
    }
    @GetMapping("/nadjiPoNazivu/{naziv}")
    public List<Vikendice> nadjiVikendicuPoNazivu(@PathVariable String naziv) {
        return new VikendiceRepo().nadjiVikendicuPoNazivu(naziv);
    }   
    @GetMapping("/nadjiPoMestu/{mesto}")
    public List<Vikendice> nadjiVikendicuPoMestu(@PathVariable String mesto) {
        return new VikendiceRepo().nadjiVikendicuPoMestu(mesto);
    }   
    @GetMapping("/nadjiPoNazivuIMestu/{naziv}/{mesto}")
    public List<Vikendice> nadjiVikendicuPoNazivuIMestu(@PathVariable String naziv, @PathVariable String mesto) {
        return new VikendiceRepo().nadjiVikendicuPoNazivuIMestu(naziv, mesto);
    }
    @GetMapping("/vikendiceVlasnika/{vlasnik}")
    public List<Vikendice> vikendiceVlasnika(@PathVariable String vlasnik) {
        return repo.vikendiceVlasnika(vlasnik);
    }
    
    @GetMapping("/slike/{idV}")
    public List<String> slikeVikendice(@PathVariable int idV) {
        List<String> urls = new ArrayList<>();
        Vikendice vikendica = repo.nadjiVikendicuPoId(idV);
        if (vikendica == null) return urls;
        String slike = vikendica.getSlike();
        if (slike == null || slike.isEmpty()) return urls;
        String[] imena = slike.split(",");
        for (String ime : imena) {
            if (ime != null && !ime.trim().isEmpty()) {
                urls.add("/uploads/" + ime.trim());
            }
        }
        return urls;
    }
    
    @DeleteMapping("/slike/{idV}/{ime}")
    public ResponseEntity<String> obrisiSliku(@PathVariable int idV, @PathVariable String ime) {
        try {
            // Ucitaj vikendicu iz baze
            Vikendice vikendica = repo.nadjiVikendicuPoId(idV);
            if (vikendica == null) {
                return ResponseEntity.status(404).body("Vikendica sa idV=" + idV + " ne postoji.");
            }
            
            String slike = vikendica.getSlike();
            if (slike == null || slike.isEmpty()) {
                return ResponseEntity.status(404).body("Nema slika za brisanje.");
            }
            
            // Razdvoji imena
            String[] imena = slike.split(",");
            List<String> noveListe = new ArrayList<>();
            boolean pronadjen = false;
            
            for (String s : imena) {
                String trimmed = s.trim();
                if (trimmed.equals(ime)) {
                    pronadjen = true;
                    // Pokusaj obrisati fajl sa diska
                    try {
                        Path putanja = Paths.get("uploads").resolve(ime);
                        Files.deleteIfExists(putanja);
                    } catch (Exception ignore) {
                        // ignorisi gresku brisanja sa diska
                    }
                } else {
                    noveListe.add(trimmed);
                }
            }
            
            if (!pronadjen) {
                return ResponseEntity.status(404).body("Slika '" + ime + "' nije pronadjena.");
            }
            
            // Azuriraj bazu sa novom listom
            vikendica.setSlike(noveListe.isEmpty() ? "" : String.join(",", noveListe));
            int rezultat = repo.urediVikendicu(vikendica);
            
            if (rezultat > 0) {
                return ResponseEntity.ok("Slika uspesno obrisana.");
            } else {
                return ResponseEntity.status(500).body("Greska pri azuriranju baze.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greska: " + e.getMessage());
        }
    }
    
    private final VikendiceRepo repo = new VikendiceRepo();
    
    @PostMapping(value = "/urediVikendicu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int urediVikendicu(
        @RequestPart("vikendica") Vikendice vikendica,
        @RequestPart(value = "slike", required = false) MultipartFile[] slike,
        @RequestPart(value = "obrisi", required = false) List<String> obrisi) {

    try {
        // Učitaj postojeću vikendicu iz baze da dobiješ stare slike
        Vikendice postojecaVikendica = repo.nadjiVikendicuPoId(vikendica.getIdV());
        List<String> svaImena = new ArrayList<>();
        
        // Ako postoje stare slike, dodaj ih u listu
        if (postojecaVikendica != null && postojecaVikendica.getSlike() != null && !postojecaVikendica.getSlike().isEmpty()) {
            String[] stareSlikes = postojecaVikendica.getSlike().split(",");
            for (String stara : stareSlikes) {
                svaImena.add(stara.trim());
            }
        }

        // Ako je prosledjeno brisanje, ukloni navedene fajlove iz liste i sa diska
        if (obrisi != null && !obrisi.isEmpty()) {
            for (String imeZaBrisanje : obrisi) {
                if (imeZaBrisanje == null || imeZaBrisanje.trim().isEmpty()) continue;
                String target = imeZaBrisanje.trim();
                // ukloni iz liste (ako postoji vise puta, ukloni sve instance)
                svaImena.removeIf(n -> n != null && n.trim().equals(target));
                try {
                    Path putanja = Paths.get("uploads").resolve(target);
                    Files.deleteIfExists(putanja);
                } catch (Exception ignore) {
                    // ignorisi gresku brisanja sa diska; bitno je da DB bude azurirana
                }
            }
        }

        // Ako su uploadovane nove slike — snimi ih i dodaj nazive
        if (slike != null && slike.length > 0) {
            for (MultipartFile file : slike) {
                if (file == null || file.isEmpty()) continue; // preskoči prazne fajlove
                String ime = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path putanja = Paths.get("uploads").resolve(ime);
                Files.createDirectories(putanja.getParent());
                Files.copy(file.getInputStream(), putanja, StandardCopyOption.REPLACE_EXISTING);
                svaImena.add(ime);
            }
        }

        // Postavi sve slike (stare + nove, nakon eventualnog brisanja)
        vikendica.setSlike(svaImena.isEmpty() ? "" : String.join(",", svaImena));

        int rezultat = repo.urediVikendicu(vikendica);
        return  rezultat;
    } catch (Exception e) {
        // bez poruka u konzoli
        return 0;
    }
}
    @PostMapping("/obrisiVikendicu")
    public int obrisiVikendicu(@RequestBody Vikendice vikendica) {
        try {
            // Ucitaj vikendicu iz baze
            Vikendice postojecaVikendica = repo.nadjiVikendicuPoId(vikendica.getIdV());
            
            // Obrisi sve slike sa diska
            String slike = postojecaVikendica.getSlike();
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
            
            // Obrisi vikendicu iz baze
            int rezultat = repo.obrisiVikendicu(postojecaVikendica);
            return rezultat;
        } catch (Exception e) {
            return 0; // bez poruka u konzoli
        }
    }
    @PostMapping("/dodajVikendicu")
    public int dodajVikendicu(@RequestBody Vikendice vikendica) {
        return repo.dodajVikendicu(vikendica);
    }

    // Dodavanje vikendice sa uploadovanim slikama (multipart)
    @PostMapping(value = "/dodajVikendicu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> dodajVikendicuMultipart(
        @RequestPart("vikendica") Vikendice vikendica,
        @RequestPart(value = "slike", required = false) MultipartFile[] slike
    ) {
        try {
            if (vikendica == null) {
                return ResponseEntity.badRequest().body("Greška: nedostaje 'vikendica' JSON deo.");
            }
            if (vikendica.getOcena() == null) {
                return ResponseEntity.badRequest().body("Greška: ocena ne sme biti null.");
            }

            // Sačuvaj uploadovane slike i formiraj CSV listu
            List<String> imena = new ArrayList<>();
            if (slike != null && slike.length > 0) {
                for (MultipartFile file : slike) {
                    if (file == null || file.isEmpty()) continue;
                    String ime = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path putanja = Paths.get("uploads").resolve(ime);
                    Files.createDirectories(putanja.getParent());
                    Files.copy(file.getInputStream(), putanja, StandardCopyOption.REPLACE_EXISTING);
                    imena.add(ime);
                }
            }
            vikendica.setSlike(imena.isEmpty() ? "" : String.join(",", imena));

            int rezultat = repo.dodajVikendicu(vikendica);
            if (rezultat > 0) return ResponseEntity.ok("Uspešno dodato: " + rezultat);
            return ResponseEntity.badRequest().body("Greška pri upisu u bazu.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Greška: " + e.getMessage());
        }
    }
    @GetMapping("/nadjiPoId/{idV}")
    public Vikendice nadjiVikendicuPoId(@PathVariable int idV) {
        return repo.nadjiVikendicuPoId(idV);
    }
}
