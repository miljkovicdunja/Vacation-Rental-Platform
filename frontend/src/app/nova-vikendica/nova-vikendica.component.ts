import { Component, inject } from '@angular/core';
import { Vikendice } from '../models/Vikendice';
import { Korisnik } from '../models/Korisnik';
import { VikendiceService } from '../services/vikendice.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-nova-vikendica',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './nova-vikendica.component.html',
  styleUrl: './nova-vikendica.component.css'
})
export class NovaVikendicaComponent {
  novaVikendica:Vikendice=new Vikendice();
  ulogovan:Korisnik=new Korisnik();
  private vikendiceService=inject(VikendiceService);
  private router=inject(Router);
  imageUrls: string[] = [];
  noveSlike:File[]=[];  
  previewUrls: string[] = [];
  message="";

  baseUrl = 'http://localhost:8080';
  ngOnInit(): void {
    let x = localStorage.getItem("ulogovan");
    if (x != null) {
      this.ulogovan = JSON.parse(x);
    }
  }

  onFilesSelected(event: any) {
      this.noveSlike=Array.from(event.target.files) as File[];
      // Generate preview URLs for newly selected files
      this.previewUrls = [];
      for (let file of this.noveSlike) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.previewUrls.push(e.target.result);
        };
        reader.readAsDataURL(file);
      }
    }

  dodajVikendicu() {
    this.novaVikendica.vlasnik = this.ulogovan.kor_ime;
    this.novaVikendica.ocena = 0;
    this.novaVikendica.telefon = this.ulogovan.kontakt_telefon;
    this.novaVikendica.status = 'aktivno';
    
    if(this.novaVikendica.naziv==""){
      this.message="Obavezan naziv";
    }
    else if(this.novaVikendica.mesto==""){
      this.message="Obavezno mesto";
    }
    else if(this.novaVikendica.cenovnikProlece==0){
      this.message="Obavezan cenovnik za prolece";
    }
    else if(this.novaVikendica.cenovnikLeto==0){
      this.message="Obavezan cenovnik za leto";
    }
    else if(this.novaVikendica.cenovnikJesen==0){
      this.message="Obavezan cenovnik za jesen";
    }
    else if(this.novaVikendica.cenovnikZima==0){
      this.message="Obavezan cenovnik za zimu";
    }
    else{
      // Send vikendica data and files separately
      this.vikendiceService.dodajVikendicu(this.novaVikendica, this.noveSlike).subscribe(data => {
        if (data) {
          alert("Vikendica je uspešno dodata!");
          this.router.navigate(['/vikendiceVlasnika']);
        } else {
          alert("Došlo je do greške pri dodavanju vikendice!");
        }
      });
    }
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
