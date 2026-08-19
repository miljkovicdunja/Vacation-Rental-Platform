import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { Router } from '@angular/router';
import { KorisnikService } from '../services/korisnik.service';

@Component({
  selector: 'app-vlasnik',
  standalone: true,
  imports: [],
  templateUrl: './vlasnik.component.html',
  styleUrl: './vlasnik.component.css'
})
export class VlasnikComponent {

  ulogovan: Korisnik = new Korisnik();
  profilnaSlikaUrl: string = '';
  router: Router = inject(Router);
  
    ngOnInit(): void {
      let x = localStorage.getItem("ulogovan");
      if (x != null) {
        this.ulogovan = JSON.parse(x);
        // Construct the full URL from profilna_slika field
        if (this.ulogovan.profilna_slika) {
          this.profilnaSlikaUrl = `http://localhost:8080/uploads/${this.ulogovan.profilna_slika}`;
        }
      }
    }
  
    azurirajPodatke() {
      this.router.navigate(['/azuriranje']);
    }
    odjaviSe(){
      localStorage.clear();
      this.router.navigate(['login']);
    }
    vidiVikendice(){
      this.router.navigate(['/vikendiceVlasnika']);
    }
    vidiRezervacije(){
      this.router.navigate(['/rezervacijeVlasnika']);
    }
    
  vidiProfil(){
    this.router.navigate(['/vlasnik']);
  }
}
