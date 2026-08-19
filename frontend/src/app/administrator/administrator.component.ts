import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { KorisnikService } from '../services/korisnik.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrator',
  standalone: true,
  imports: [],
  templateUrl: './administrator.component.html',
  styleUrl: './administrator.component.css'
})
export class AdministratorComponent {
  private korisnikServis = inject(KorisnikService);
  private router=inject(Router);
  korisnici:Korisnik[]=[];
  cekanje:Korisnik[]=[];
  ngOnInit(): void {
    this.korisnikServis.sviKorisnici().subscribe(data=>{
      this.korisnici=data;
    });
    this.korisnikServis.korisniciNaCekanju().subscribe(data=>{
      this.cekanje=data;
    });
  }
  azurirajKorisnika(korisnik:Korisnik){
    localStorage.setItem("korisnikZaIzmenu",JSON.stringify(korisnik));
    this.router.navigate(['/azuriranje']);
  }
  obrisiKorisnika(korisnik:Korisnik){
    this.korisnikServis.obrisiKorisnika(korisnik).subscribe(data=>{
      if(data){
        this.korisnikServis.sviKorisnici().subscribe(data=>{
          this.korisnici=data;
        });
      }
      else{
        alert("Doslo je do greske pri brisanju korisnika!");
      } 
    });
  }
  deaktivirajKorisnika(korisnik:Korisnik){
    this.korisnikServis.deaktivirajKorisnika(korisnik).subscribe(data=>{
      if(data){
        this.korisnikServis.sviKorisnici().subscribe(data=>{
          this.korisnici=data;
        });
      }
      else{
        alert("Doslo je do greske pri deaktiviranju korisnika!");
      }
    });
  } 
  prihvati(korisnik:Korisnik){
    this.korisnikServis.prihvatiKorisnika(korisnik).subscribe(data=>{
      if(data){
        this.korisnikServis.korisniciNaCekanju().subscribe(data=>{
          this.cekanje=data;
        });
      }
      else{
        alert("Doslo je do greske pri prihvatanju korisnika!");
      }
    });
  }
  odbij(korisnik:Korisnik){
    this.korisnikServis.odbijKorisnika(korisnik).subscribe(data=>{
      if(data){
        this.korisnikServis.korisniciNaCekanju().subscribe(data=>{
          this.cekanje=data;
        });
      }
      else{
        alert("Doslo je do greske pri odbijanju korisnika!");
      }
    });
  }
  nazad(){ 
    localStorage.clear();
    this.router.navigate(['login']);
  }
}
