import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { Router } from '@angular/router';
import { VikendiceService } from '../services/vikendice.service';
import { Vikendice } from '../models/Vikendice';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-vlasnik-vikendice',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './vlasnik-vikendice.component.html',
  styleUrl: './vlasnik-vikendice.component.css'
})
export class VlasnikVikendiceComponent {
  ulogovan: Korisnik = new Korisnik();
    router:Router=inject(Router);
    private vikendiceService = inject(VikendiceService);
    vikendice:Vikendice[]=[];
    novaVikendica:Vikendice=new Vikendice();
  
    ngOnInit(): void {
      let x = localStorage.getItem("ulogovan");
      if (x != null) {
        this.ulogovan = JSON.parse(x);
      }
      this.vikendiceService.vikendiceVlasnika(this.ulogovan.kor_ime).subscribe(data=>{
        this.vikendice=data;
      });
    }
      azuriraj(vikendica:Vikendice){
        localStorage.setItem("vikendicaZaIzmenu",JSON.stringify(vikendica));
        this.router.navigate(['/azurirajVikendicu']);
      }
      
    obrisiVikendicu(vikendica:Vikendice) {
      if (confirm('Da li ste sigurni da želite obrisati vikendicu "' + vikendica.naziv + '"?')) {
        this.vikendiceService.obrisiVikendicu(vikendica).subscribe(data=>{
          if(data){
            this.vikendiceService.vikendiceVlasnika(this.ulogovan.kor_ime).subscribe(data => {
                this.vikendice = data;
              });
            }
            else{
                alert("Doslo je do greske pri brisanju!");
            }
          });
        }
      }
    dodajNovuVikendicu(){
      this.router.navigate(['/novaVikendica']);
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
