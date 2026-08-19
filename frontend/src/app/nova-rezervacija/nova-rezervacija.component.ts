import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Korisnik } from '../models/Korisnik';
import { Vikendice } from '../models/Vikendice';
import { Rezervacije } from '../models/Rezervacije';
import { VikendiceService } from '../services/vikendice.service';
import { RezervacijeService } from '../services/rezervacije.service';

@Component({
  selector: 'app-nova-rezervacija',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './nova-rezervacija.component.html',
  styleUrl: './nova-rezervacija.component.css'
})
export class NovaRezervacijaComponent implements OnInit {
  ulogovan: Korisnik = new Korisnik();
  novaRezervacija: Rezervacije = new Rezervacije();
  vikendice: Vikendice[] = [];
  selectedVikendica?: Vikendice;
  
  trenutno: number = 1;
  ukupno: number = 4;
  message: string = '';
  
  private vikendicaServis = inject(VikendiceService);
  private rezervacijeServis = inject(RezervacijeService);
  private router = inject(Router);

  ngOnInit(): void {
    let x = localStorage.getItem("ulogovan");
    if (x != null) {
      this.ulogovan = JSON.parse(x);
      this.novaRezervacija.turista = this.ulogovan.kor_ime;
      this.novaRezervacija.kartica=this.ulogovan.broj_kreditne_kartice;
    }
    this.vikendicaServis.sveVikendice().subscribe(data => {
      this.vikendice = data;
    });
  }
  sledece(): void {
    if (this.trenutniKorak()) {
      if (this.trenutno < this.ukupno) {
        this.trenutno++;
        this.message = '';
      }
    }
  }

  nazad(): void {
    if (this.trenutno > 1) {
      this.trenutno--;
      this.message = '';
    }
  }

  promeniKorak(step: number): void {
    if (step <= this.trenutno || this.sledeciKorak(step - 1)) {
      this.trenutno = step;
      this.message = '';
    }
  }

  trenutniKorak(): boolean {
    switch (this.trenutno) {
      case 1:
        if (!this.novaRezervacija.idV) {
          this.message = 'Molimo izaberite vikendicu';
          return false;
        }
        return true;
      case 2:
        if (!this.novaRezervacija.datumPocetak) {
          this.message = 'Molimo unesite datum pocetka';
          return false;
        }
        if (!this.novaRezervacija.datumKraj) {
          this.message = 'Molimo unesite datum kraja';
          return false;
        }
        if (new Date(this.novaRezervacija.datumPocetak) >= new Date(this.novaRezervacija.datumKraj)) {
          this.message = 'Datum kraja mora biti posle datuma pocetka';
          return false;
        }
        if (this.novaRezervacija.brOdraslih < 1) {
          this.message = 'Mora biti najmanje 1 odrasla osoba';
          return false;
        }
        return true;
      case 3:
        if (!this.novaRezervacija.kartica || this.novaRezervacija.kartica.length < 16) {
          this.message = 'Molimo unesite validan broj kartice (16 cifara)';
          return false;
        }
        return true;
      case 4:
        return true;
      default:
        return false;
    }
  }

  sledeciKorak(step: number): boolean {
    for (let i = 1; i <= step; i++) {
      const current = this.trenutno;
      this.trenutno = i;
      if (!this.trenutniKorak()) {
        this.trenutno = current;
        return false;
      }
    }
    this.trenutno = step;
    return true;
  }

  odaberi(vikendica: Vikendice): void {
    this.selectedVikendica = vikendica;
    this.novaRezervacija.idV = vikendica.idV;
  }

  cena(): number {
    if (!this.selectedVikendica || !this.novaRezervacija.datumPocetak || !this.novaRezervacija.datumKraj) {
      return 0;
    }
    
    const pocetak = new Date(this.novaRezervacija.datumPocetak);
    const kraj = new Date(this.novaRezervacija.datumKraj);
    const dani = Math.ceil((kraj.getTime() - pocetak.getTime()) / (1000 * 60 * 60 * 24));

    const mesec = pocetak.getMonth() + 1;
    let cenaPoDanu = 0;

    if (mesec >= 3 && mesec <= 5) {
      cenaPoDanu = this.selectedVikendica.cenovnikProlece;
    } else if (mesec >= 6 && mesec <= 8) {
      cenaPoDanu = this.selectedVikendica.cenovnikLeto;
    } else if (mesec >= 9 && mesec <= 11) {
      cenaPoDanu = this.selectedVikendica.cenovnikJesen;
    } else {
      cenaPoDanu = this.selectedVikendica.cenovnikZima;
    }
    
    return dani * cenaPoDanu;
  }

  rezervisi(): void {
    if (!this.trenutniKorak()) {
      return;
    }
    
    
    this.rezervacijeServis.dodajRezervaciju(this.novaRezervacija).subscribe(data=>{
      if(data){ 
        this.message = 'Rezervacija uspeano kreirana!';
        this.router.navigate(['/turista']);
      }else
        this.message = 'Greska pri kreiranju rezervacije.';
    });
  }
  
  nazadStrana(){ 
    this.router.navigate(['turistaRezervacije']);
  }
}
