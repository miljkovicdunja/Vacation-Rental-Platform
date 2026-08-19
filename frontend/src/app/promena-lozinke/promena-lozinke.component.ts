import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { KorisnikService } from '../services/korisnik.service';

@Component({
  selector: 'app-promena-lozinke',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './promena-lozinke.component.html',
  styleUrl: './promena-lozinke.component.css'
})
export class PromenaLozinkeComponent {
    router:Router=inject(Router);
    korisnicko_ime:string="";
    lozinka="";
    staraLozinka: string = "";
    novaLozinka: string = "";
    ponovljenjaNovaLozinka: string = "";
    message="";
    private korisnikServis = inject(KorisnikService)
  
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
  
    async sacuvajIzmene() {
      const lozinkaRegex = /^(?=[A-Za-z])(?=(?:.*[a-z]){3,})(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|.<>/?]).{6,10}$/;
      const hashStara=await this.hashPassword(this.staraLozinka);
      this.korisnikServis.dohvatiLozinku(this.korisnicko_ime).subscribe(data=>{
        if(data!=null){
          this.lozinka=data;
        }
      
      if (hashStara!== this.lozinka) {
        this.message = "Uneta stara lozinka nije ispravna!";
        return;
    }});
      if (this.novaLozinka== "") {
        this.message="Nova lozinka ne sme biti prazna!";
      }
      else if (this.novaLozinka !== this.ponovljenjaNovaLozinka) {
        this.message="Nova lozinka i ponovljena nova lozinka se ne poklapaju!";
      }
      else if(!lozinkaRegex.test(this.novaLozinka)) {
        this.message="Nova lozinka mora imati 6-10 karaktera, bar jedno veliko slovo, bar jedno malo slovo i bar jedan specijalni karakter i mora pocinjati slovom!";
      }
      else{
        const hashNova = await this.hashPassword(this.novaLozinka);
        this.korisnikServis.promenaLozinke(this.korisnicko_ime, hashNova).subscribe(data => {
          if (data != null) {
            this.message="Uspesno promenjena lozinka!";
          } else {
            this.message="Greska prilikom promene lozinke!";
          }
      });
      localStorage.clear();
    this.router.navigate(['login']);
      }
    }
}
