import { Component, inject } from '@angular/core';
import { KorisnikService } from '../services/korisnik.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Korisnik } from '../models/Korisnik';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prijavi-se',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './prijavi-se.component.html',
  styleUrl: './prijavi-se.component.css'
})
export class PrijaviSeComponent {
    private korisnikServis = inject(KorisnikService)
    private router = inject(Router)
    korisnik:Korisnik=new Korisnik();
    korisnici:Korisnik[]=[];
    
  selectedFile: File | null = null;
  fileName: string = '';
  greska="";
  lozinkaKorisnika="";
  karticaSlika: string | null = null;

  ngOnInit(): void {
    this.korisnikServis.sviKorisnici().subscribe(data=>{
      this.korisnici=data;
    });
  }


  private async hashPassword(password: string): Promise<string> {
      const encoder = new TextEncoder();
      const data = encoder.encode(password);
      const hashBuffer = await crypto.subtle.digest('SHA-256', data);

    // tipizujemo kao number[]
      const hashArray: number[] = Array.from(new Uint8Array(hashBuffer));

    const hashHex = hashArray
      .map(b => b.toString(16).padStart(2, '0'))
      .join('');

    return hashHex.toLowerCase();
}
  private detektujKarticu(digits: string) {
    this.karticaSlika = null;

  //diners kartica
    const prefix2 = digits.slice(0, 2);
    const prefix3 = digits.slice(0, 3);
    const prefix3num = parseInt(prefix3, 10);
    if ((prefix3num >= 300 && prefix3num <= 303) || prefix2 === '36' || prefix2 === '38') {
    this.karticaSlika = 'dinersclub.jpg';
    return;
  }

  //master kartica
  const prefix2MC = parseInt(digits.slice(0, 2), 10);
  if (prefix2MC >= 51 && prefix2MC <= 55) {
    this.karticaSlika = 'mastercard.png';
    return;
  }
  const prefixesVisa = ['4539', '4556', '4916', '4532', '4929', '4485', '4716'];
  const prefix4 = digits.slice(0, 4);
  if (prefixesVisa.includes(prefix4)) {
    this.karticaSlika = 'visa.png';
    return;
  }
  }

  private isValidCardPrefix(digits: string): boolean {
    if (digits.length < 2) return false;

    // Diners Club: 300-303, 36, 38
    const prefix2 = digits.slice(0, 2);
    const prefix3 = digits.slice(0, 3);
    const prefix3num = parseInt(prefix3, 10);
    if ((prefix3num >= 300 && prefix3num <= 303) || prefix2 === '36' || prefix2 === '38') {
      return true;
    }

    // MasterCard: 51-55
    const prefix2MC = parseInt(digits.slice(0, 2), 10);
    if (prefix2MC >= 51 && prefix2MC <= 55) {
      return true;
    }

    // Visa: specific prefixes
    const prefixesVisa = ['4539', '4556', '4916', '4532', '4929', '4485', '4716'];
    const prefix4 = digits.slice(0, 4);
    if (prefixesVisa.includes(prefix4)) {
      return true;
    }

    return false;
  }

  onInputChange(event: any) {
    const input = event.target as HTMLInputElement;
    let digits = input.value.replace(/\D/g, ''); // samo cifre

    // automatsko formatiranje (razmaci posle svake 4 cifre)
    const formatted = digits.replace(/(.{4})/g, '$1 ').trim();
    this.korisnik.broj_kreditne_kartice = formatted;

    // detekcija tipa
    this.detektujKarticu(digits);
  }
    onFileSelected(event: any) {
      const file: File = event.target.files[0];
    if (!file) return;

    this.selectedFile = file;
    this.fileName = file.name; // samo ime fajla
    }

    async dodaj(){
      const lozinkaRegex = /^(?=[A-Za-z])(?=(?:.*[a-z]){3,})(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|.<>/?]).{6,10}$/;
      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      const phoneRegex = /^\+[\d\s-]{8,20}$/;
      const lozinka=await this.hashPassword(this.korisnik.lozinka);
      if(this.korisnik.kor_ime==""){
        this.greska="Morate uneti korisnicko ime!";
        return;
      }
      
      // Check if username already exists
      const usernameExists = this.korisnici.some(k => k.kor_ime === this.korisnik.kor_ime);
      if (usernameExists) {
        this.greska = "Korisnicko ime je zauzeto!";
        return;
      }
      
      if(this.korisnik.lozinka==""){
        this.greska="Morate uneti lozinku";
        return;
      }
      if(!lozinkaRegex.test(this.korisnik.lozinka)) {
        this.greska="Lozinka mora imati 6-10 karaktera, bar jedno veliko slovo, bar jedno malo slovo i bar jedan specijalni karakter i mora pocinjati slovom!";
        return;
      }
      if(this.korisnik.pol==""){
        this.greska="Morate uneti pol";
        return;
      }
      if(this.korisnik.ime==""){
        this.greska="Morate uneti ime";
        return;
      }
      if(this.korisnik.prezime==""){
        this.greska="Morate uneti prezime";
        return;
      }
      if(this.korisnik.mejl==""){
        this.greska="Morate uneti mejl";
        return;
      }
      if(!emailRegex.test(this.korisnik.mejl)){
        this.greska="Mejl nije u ispravnom formatu";
        return;
      }
      
      // Check if email already exists
      const emailExists = this.korisnici.some(k => k.mejl === this.korisnik.mejl);
      if (emailExists) {
        this.greska = "Mejl je vec registrovan!";
        return;
      }
      
      if(this.korisnik.tip==""){
        this.greska="Morate uneti tip";
        return;
      }
      if(this.korisnik.kontakt_telefon=="")
      {
        this.greska="Morate uneti kontakt telefon";
        return;
      }
      if(!phoneRegex.test(this.korisnik.kontakt_telefon)){
        this.greska="Telefon nije u ispravnom formatu";
        return;
      }
      if(this.korisnik.broj_kreditne_kartice==""){
        this.greska="Morate uneti broj kreditne kartice";
        return;
      }
      
      // Validate card number starts with valid prefixes
      const digits = this.korisnik.broj_kreditne_kartice.replace(/\D/g, '');
      if (!this.isValidCardPrefix(digits)) {
        this.greska = "Broj kreditne kartice mora biti Visa, MasterCard ili Diners Club!";
        return;
      }
      
      // All validations passed, proceed with registration
      this.korisnik.lozinka=lozinka;
      
      // Use multipart endpoint if file is selected, otherwise use regular endpoint
      if (this.selectedFile) {
        this.korisnikServis.dodajKorisnikaMultipart(this.korisnik, this.selectedFile).subscribe(data=>{
          if(data>0){
            this.greska="Uspesno dodato!";
            this.router.navigate(['login']);
          }
          else{
            this.greska="Doslo je do greske!";
          }
        });
      } else {
        this.korisnikServis.dodajKorisnika(this.korisnik).subscribe(data=>{
          if(data>0){
            this.greska="Uspesno dodato!";
            this.router.navigate(['login']);
          }
          else{
            this.greska="Doslo je do greske!";
          }
        });
      }
    }
    
  nazad(){ 
    localStorage.clear();
    this.router.navigate(['login']);
  }
}
