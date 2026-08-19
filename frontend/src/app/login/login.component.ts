import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { KorisnikService } from '../services/korisnik.service';
import { FormsModule } from '@angular/forms';
import { Korisnik } from '../models/Korisnik';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private korisnikServis = inject(KorisnikService)
  private router = inject(Router)

  username: string = "";
  password: string = "";
  tip: string = "";
  greska: string = "";

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

  async login() {
    if (this.username == "") {
      this.greska = "Nije uneto korisnicko ime";
    }
    else if (this.password == "") {
      this.greska = "Nije uneta lozinka";
    }
    else if (this.tip == "") {
      this.greska = "Nije unet tip";
    }
    else {
        const lozinka= await this.hashPassword(this.password);
        this.korisnikServis.login(this.username, lozinka, this.tip).subscribe(async data => {
          
          if (!data) {
            this.greska = 'Takav korisnik u bazi ne postoji';
            return;
          } 
          const hashUneta=await this.hashPassword(this.password);
          if (hashUneta !== data.lozinka) {
          this.greska = "Uneta lozinka nije ispravna!";
          return;
        }
        if(data.status!='odobreno'){
            this.greska = "Nalog nije aktiviran ili je deaktiviran!";
        }
          else {
            localStorage.setItem('ulogovan', JSON.stringify(data));
            if (data.tip == 'turista') {
              this.router.navigate(['/turista']);
            } else if (data.tip == 'vlasnik') {
              this.router.navigate(['/vlasnik']);
            } else {
              this.greska = 'Nepoznat tip korisnika';
            }
          }
        });
    }
  }
  loginAdministrator(){
    this.router.navigate(['/LoginAdministrator'])
  }
  promeniLozinku() {
    this.router.navigate(['/promenaLozinke']);
  }
  prijaviSe(){
    this.router.navigate(['/prijaviSe']);
  }
  neregistrovani(){
    this.router.navigate(['']);
  }
}
