import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { Router } from '@angular/router';
import { KorisnikService } from '../services/korisnik.service';

@Component({
  selector: 'app-turista',
  standalone: true,
  imports: [],
  templateUrl: './turista.component.html',
  styleUrl: './turista.component.css'
})
export class TuristaComponent {
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
    this.router.navigate(['/turistaVikendice']);
  }
  vidiProfil(){
    this.router.navigate(['/turista']);
  }
  vidiRezervacije(){
    this.router.navigate(['/turistaRezervacije'])
  }
}
