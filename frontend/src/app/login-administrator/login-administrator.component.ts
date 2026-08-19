import { Component, inject } from '@angular/core';
import { KorisnikService } from '../services/korisnik.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-administrator',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login-administrator.component.html',
  styleUrl: './login-administrator.component.css'
})
export class LoginAdministratorComponent {
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
      else {
        this.tip="administrator"
        const lozinka= await this.hashPassword(this.password);
        this.korisnikServis.login(this.username, lozinka, this.tip).subscribe(async data => {
          if (!data) {
            this.greska = 'Takav korisnik u bazi ne postoji';
            return;
          } 
          const hashUneta=await this.hashPassword(this.password);
          if (hashUneta !== data.lozinka) {
          this.greska = "Uneta stara lozinka nije ispravna!";
          return;
        }
        else {
          localStorage.setItem('ulogovan', JSON.stringify(data));
          this.router.navigate(['/administrator']);
        }
      });
      }
    }
    
  nazad(){ 
    localStorage.clear();
    this.router.navigate(['login']);
  }
}
