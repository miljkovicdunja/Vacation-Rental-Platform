import { Component, inject } from '@angular/core';
import { VikendiceService } from '../services/vikendice.service';
import { KorisnikService } from '../services/korisnik.service';
import { Vikendice } from '../models/Vikendice';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-turista-vikendice',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './turista-vikendice.component.html',
  styleUrl: './turista-vikendice.component.css'
})
export class TuristaVikendiceComponent {
  private vikendiceService = inject(VikendiceService);
    private korisnikService = inject(KorisnikService);
    sortiranoOpadajuce:Vikendice[]=[];
    sortiranoRastuce:Vikendice[]=[];
    mesto="";
    naziv="";
    vikendice:Vikendice[]=[];
    
  selektovanaOpcija: string = 'rastuceNaziv';
  ngOnInit(): void {
    if(this.selektovanaOpcija==='opadajuceNaziv'){
      this.sortirajOpadajuce();
    }else if(this.selektovanaOpcija==='rastuceNaziv'){
      this.sortirajRastuce();
    }
    else if(this.selektovanaOpcija==='opadajuceMesto'){
      this.sortirajMestoOpadajuce();
    }else if(this.selektovanaOpcija==='rastuceMesto'){
      this.sortirajMestoRastuce();
    }
  }
  sortirajOpadajuce(){
    this.vikendiceService.sortirajOpadajuce().subscribe(data => {
      this.vikendice = data;
      this.ngOnInit();
    });
  }
  sortirajRastuce(){
    this.vikendiceService.sortirajRastuce().subscribe(data => {
      this.vikendice = data;
      this.ngOnInit();
    });
  }
  sortirajMestoOpadajuce(){
    this.vikendiceService.sortirajMestoOpadajuce().subscribe(data => {
      this.vikendice = data;
      this.ngOnInit();
    });
  }
  sortirajMestoRastuce(){
    this.vikendiceService.sortirajMestoRastuce().subscribe(data => {
      this.vikendice = data;
      this.ngOnInit();
    });
  }

  brojZvezdica(puno:number, ocena:number)
  {
    const puna = Math.floor(ocena);
  const decimala = (ocena - puna) * 100;

  if (puno <= puna) return '100%';           // puna zvezdica
  if (puno === puna + 1) return `${decimala}%`; // delimično popunjena
  return '0%';
  }

  router:Router=inject(Router);
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
  sacuvaj(vikendica:Vikendice){
    localStorage.setItem("vikendicaDetalji",JSON.stringify(vikendica));
  }
}
