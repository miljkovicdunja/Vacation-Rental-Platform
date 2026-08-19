import { Component, inject } from '@angular/core';
import { VikendiceService } from '../services/vikendice.service';
import { KorisnikService } from '../services/korisnik.service';
import { Vikendice } from '../models/Vikendice';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RezervacijeService } from '../services/rezervacije.service';

@Component({
  selector: 'app-neregistrovani',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './neregistrovani.component.html',
  styleUrl: './neregistrovani.component.css'
})
export class NeregistrovaniComponent {
  private vikendiceService = inject(VikendiceService);
  private korisnikService = inject(KorisnikService);
  private router=inject(Router);
  sortiranoOpadajuce:Vikendice[]=[];
  sortiranoRastuce:Vikendice[]=[];
  private rezervacijeService=inject(RezervacijeService);
  mesto="";
  naziv="";
  vikendice:Vikendice[]=[];
  brojVikendica: number = 0;
  brojTurista: number = 0;
  brojVlasnika: number = 0;
  brojRezervacija24h: number = 0;
  brojRezervacija7dana:number=0;
  brojRezervacija30dana:number=0;
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
    this.vikendiceService.brojVikendica().subscribe(data => {
      this.brojVikendica = data;
    });
    this.korisnikService.brojTurista().subscribe(data => {
      this.brojTurista = data;
    });
    this.korisnikService.brojVlasnika().subscribe(data => {
      this.brojVlasnika = data;
    });
    this.rezervacijeService.brojRezervacija24h().subscribe((data: any) => {
      this.brojRezervacija24h = data;
    });
    this.rezervacijeService.brojRezervacija7dana().subscribe((data:any)=>{
      this.brojRezervacija7dana=data;
    });
    this.rezervacijeService.brojRezervacija30dana().subscribe((data:any)=>{
      this.brojRezervacija30dana=data;
    });
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
  login(){
    this.router.navigate(['login']);
  }
}