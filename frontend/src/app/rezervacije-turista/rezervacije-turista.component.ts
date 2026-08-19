import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { Router } from '@angular/router';
import { Vikendice } from '../models/Vikendice';
import { Rezervacije } from '../models/Rezervacije';
import { RezervacijeService } from '../services/rezervacije.service';
import { VikendiceService } from '../services/vikendice.service';

@Component({
  selector: 'app-rezervacije-turista',
  standalone: true,
  imports: [],
  templateUrl: './rezervacije-turista.component.html',
  styleUrl: './rezervacije-turista.component.css'
})
export class RezervacijeTuristaComponent {
  ulogovan: Korisnik = new Korisnik();
  private rezervacijeServis=inject(RezervacijeService)
  private vikendicaServis=inject(VikendiceService);
  router:Router=inject(Router);
  vikendice:Vikendice[]=[];
  rezervacije:Rezervacije[]=[];
  
  ngOnInit(): void {
      let x = localStorage.getItem("ulogovan");
      if (x != null) {
        this.ulogovan = JSON.parse(x);
      }
      this.rezervacijeServis.trenutneRezervacijeTuriste(this.ulogovan.kor_ime).subscribe(data => {
        this.rezervacije = data;

      for (let rez of this.rezervacije) {
      this.vikendicaServis.nadjiVikendicuPoId(rez.idV).subscribe(data1 => {
        let vikendica = data1;

        if (!this.vikendice.find(v => v.idV === vikendica.idV)) {
          this.vikendice.push(vikendica);
        }
      });
    }
  });
  }
  dodajNovuRezervaciju(){
    this.router.navigate(['/novaRezervacija']);
  }

  // Show button only if today's date is more than 1 full day before datumPocetak
  canShowButton(rez: Rezervacije): boolean {
    if (!rez?.datumPocetak) return false;
    const start = this.toDateOnly(rez.datumPocetak);
    const today = this.toDateOnly(new Date());
    if (!start || !today) return false;
    const msPerDay = 1000 * 60 * 60 * 24;
    const diffDays = Math.floor((start.getTime() - today.getTime()) / msPerDay);
    return diffDays > 1;
  }

  // Normalize any input to a Date-only (local) object (YYYY-MM-DD, time = 00:00)
  private toDateOnly(value: any): Date | null {
    const d = value instanceof Date ? value : new Date(value);
    if (isNaN(d.getTime())) return null;
    return new Date(d.getFullYear(), d.getMonth(), d.getDate());
  }
  otkazi(rezervacija:Rezervacije){
    this.rezervacijeServis.otkaziRezervaciju(rezervacija).subscribe(data=>{
      if(data){
        this.rezervacijeServis.trenutneRezervacijeTuriste(this.ulogovan.kor_ime).subscribe(data => {
          this.rezervacije = data;
        });
      }});
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