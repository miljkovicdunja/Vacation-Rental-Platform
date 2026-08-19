import { Component, inject } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { FormsModule } from '@angular/forms';
import { KorisnikService } from '../services/korisnik.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-azuriranje',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './azuriranje.component.html',
  styleUrl: './azuriranje.component.css'
})
export class AzuriranjeComponent {
  ulogovan: Korisnik = new Korisnik();
  private korisnikServis = inject(KorisnikService)
  selectedFile: File | null = null;
  fileName: string = '';
  previewUrl: string | null = null;
  profilnaSlikaUrl: string = '';
  
    ngOnInit(): void {
      let x = localStorage.getItem("ulogovan");
      if (x != null) {
        this.ulogovan = JSON.parse(x);
        // Construct the full URL from profilna_slika field
        if (this.ulogovan.profilna_slika) {
          this.profilnaSlikaUrl = `http://localhost:8080/uploads/${this.ulogovan.profilna_slika}`;
        }
      }
    }

    onFileSelected(event: any) {
      const file: File = event.target.files[0];
      if (!file) return;

      this.selectedFile = file;
      this.fileName = file.name;

      // Show preview of selected image
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.previewUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }

    sacuvajIzmene() {
      this.korisnikServis.azurirajKorisnika(this.ulogovan, this.selectedFile || undefined)
        .subscribe({
          next: (result) => {
            if (result === 1) {
              // Fetch updated user data from backend to get the new profilna_slika filename
              this.korisnikServis.dohvatiKorisnika(this.ulogovan.kor_ime)
                .subscribe({
                  next: (updatedKorisnik) => {
                    // Update local data with new info from backend
                    this.ulogovan = updatedKorisnik;
                    localStorage.setItem('ulogovan', JSON.stringify(this.ulogovan));
                    
                    // Update profile image URL with new filename
                    if (this.ulogovan.profilna_slika) {
                      this.profilnaSlikaUrl = `http://localhost:8080/uploads/${this.ulogovan.profilna_slika}`;
                    }
                    
                    alert('Podaci uspešno ažurirani!');
                    this.selectedFile = null;
                    this.previewUrl = null;
                  },
                  error: (err) => {
                    console.error('Error fetching updated user:', err);
                    alert('Podaci su ažurirani, ali greška pri učitavanju podataka.');
                  }
                });
            } else {
              alert('Greška prilikom ažuriranja podataka.');
            }
          },
          error: (err) => {
            console.error('Update error:', err);
            alert('Greška prilikom ažuriranja podataka.');
          }
        });
    }

    router:Router=inject(Router);
      odjaviSe(){
    localStorage.clear();
    this.router.navigate(['login']);
  }
  vidiVikendice(){
    if(this.ulogovan.tip=="turista")
    {
      this.router.navigate(['/turistaVikendice']);
    }
    else{
      this.router.navigate(['/vikendiceVlasnika']);
    }
  }
  vidiProfil(){
    if(this.ulogovan.tip=="turista")
    {
      this.router.navigate(['/turista']);
    }
    else{
      this.router.navigate(['/vlasnik']);
    }
  }
  
  vidiRezervacije(){
    if(this.ulogovan.tip=="turista")
    {
      this.router.navigate(['/turistaRezervacije']);
    }
    else{
      this.router.navigate(['/rezervacijeVlasnika']);
    }
  }
}
