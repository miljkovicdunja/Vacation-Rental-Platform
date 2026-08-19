import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/Korisnik';
import { RezervacijeService } from '../services/rezervacije.service';
import { VikendiceService } from '../services/vikendice.service';
import { Vikendice } from '../models/Vikendice';
import { Rezervacije } from '../models/Rezervacije';
import { FormsModule } from '@angular/forms';
// Using native Date for formatting today's date-time

@Component({
  selector: 'app-rezervacije-vlasnik',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './rezervacije-vlasnik.component.html',
  styleUrl: './rezervacije-vlasnik.component.css'
})
export class RezervacijeVlasnikComponent {
  ulogovan: Korisnik = new Korisnik();
    private rezervacijeServis=inject(RezervacijeService)
    private vikendicaServis=inject(VikendiceService);
    router:Router=inject(Router);
    vikendice:Vikendice[]=[];
    rezervacije:Rezervacije[]=[];
    rezervacija:Rezervacije=new Rezervacije();
    odbijeno=false;
    idRez=0;
    disabled=false;
    komentar="";
    // Today in format suitable for datetime-local inputs (YYYY-MM-DDTHH:mm)
    danas: string = "";
    
    
    
    ngOnInit(): void {
        let x = localStorage.getItem("ulogovan");
        if (x != null) {
          this.ulogovan = JSON.parse(x);
        }
        this.rezervacijeServis.neobradjeneRezervacije(this.ulogovan).subscribe(data => {
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
  // Set today's date-time without hardcoding, formatted for datetime-local input (YYYY-MM-DDTHH:mm)
  const now = new Date();
  const local = new Date(now.getTime() - now.getTimezoneOffset() * 60000);
  this.danas = local.toISOString().slice(0,16);
    }
    prihvatiRezervaciju(rezervacija:number){
      this.rezervacijeServis.prihvatiRezervaciju(rezervacija).subscribe(data=>{
        if(data){
          this.rezervacijeServis.neobradjeneRezervacije(this.ulogovan).subscribe(data => {
            this.rezervacije = data;
          });
        }
        else{
          alert("Doslo je do greske pri prihvatanju rezervacije!");
        }
    });}
    odbijRezervaciju(){
      this.odbijeno=false;
      this.disabled=false;
      this.rezervacijeServis.odbijRezervaciju(this.idRez, this.komentar).subscribe(data=>{
        if(data){
          this.rezervacijeServis.neobradjeneRezervacije(this.ulogovan).subscribe(data => {
            this.rezervacije = data;
          });
        }
        else{
          alert("Doslo je do greske pri odbijanju rezervacije!");
        }
      });
      this.idRez=0;
    }
    odbij(indeks:number){
      this.odbijeno=true;
      this.idRez=indeks;
      this.disabled=true;
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
