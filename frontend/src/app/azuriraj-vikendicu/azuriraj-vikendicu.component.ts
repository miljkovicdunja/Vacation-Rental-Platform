import { Component, inject } from '@angular/core';
import { Vikendice } from '../models/Vikendice';
import { Korisnik } from '../models/Korisnik';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { VikendiceService } from '../services/vikendice.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-azuriraj-vikendicu',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './azuriraj-vikendicu.component.html',
  styleUrl: './azuriraj-vikendicu.component.css'
})
export class AzurirajVikendicuComponent {
  vikendicaZaIzmenu:Vikendice=new Vikendice();
  ulogovan:Korisnik=new Korisnik();
  novaVikendica={idV:0,naziv:'',mesto:'',usluge:'',cenovnikProlece:'',cenovnikLeto:'',cenovnikJesen:'',cenovnikZima:'',telefon:'',koordinate:'',vlasnik:'',slike:[] as File[], ocena:0, status:''};
  vikendicaServis=inject(VikendiceService);
  noveSlike:File[]=[];
  postojeceSlike: string[] = [];
  previewUrls: string[] = [];
  
  ngOnInit(): void {
    let x = localStorage.getItem("vikendicaZaIzmenu");
    if (x != null) {
      this.vikendicaZaIzmenu = JSON.parse(x);
      this.ucitajPostojeceSlike();
    }
    let y = localStorage.getItem("ulogovan");
    if (y != null) {
      this.ulogovan = JSON.parse(y);
    }
    if (this.vikendicaZaIzmenu && this.vikendicaZaIzmenu.idV) {
      this.loadImages(this.vikendicaZaIzmenu.idV);
    }
  }
  imageUrls: string[] = [];
  baseUrl = 'http://localhost:8080';

  loadImages(idV: number) {
    this.vikendicaServis.getSlikeVikendice(idV).subscribe({
      next: (urls) => {
        // Store relative paths in postojeceSlike for backend
        this.postojeceSlike = urls;
        // Store full URLs for display
        this.imageUrls = urls.map(url => this.baseUrl + url);
      },
      error: (err) => {
        console.error('Error loading images:', err);
        this.imageUrls = [];
        this.postojeceSlike = [];
      }
    });
  }
  router:Router=inject(Router);
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

    ucitajPostojeceSlike() {
      // Parse existing images from string or array
      if (this.vikendicaZaIzmenu.slike) {
        if (typeof this.vikendicaZaIzmenu.slike === 'string') {
          this.postojeceSlike = (this.vikendicaZaIzmenu.slike as any).split(',').filter((s: string) => s.trim());
        } else if (Array.isArray(this.vikendicaZaIzmenu.slike)) {
          // If slike is already an array
          this.postojeceSlike = this.vikendicaZaIzmenu.slike as any;
        }
      }
    }

    ukloniPostojecuSliku(index: number) {
      // Get the relative path before removing
      const slikaPath = this.postojeceSlike[index];
      
      // Call backend to delete the image file and from database
      if (this.vikendicaZaIzmenu.idV && slikaPath) {
        this.deleteImage(this.vikendicaZaIzmenu.idV, slikaPath);
      }
      
      // Remove from local arrays
      this.postojeceSlike.splice(index, 1);
      this.imageUrls.splice(index, 1);
    }

    ukloniNovuSliku(index: number) {
      this.noveSlike.splice(index, 1);
      this.previewUrls.splice(index, 1);
    }

    sacuvajIzmene() {
      localStorage.setItem("ulogovan", JSON.stringify(this.ulogovan));
      // Send the edited object directly so all changes are saved
      this.vikendicaServis.urediVikendicu(this.vikendicaZaIzmenu, this.noveSlike, this.postojeceSlike).subscribe(data => {
        if (data != null) {
          alert("Uspesno azurirani podaci");
          if (this.vikendicaZaIzmenu.idV) {
            this.loadImages(this.vikendicaZaIzmenu.idV);
          }
        } else {
          alert("Greska prilikom azuriranja podataka");
        }
      });
    }
    deleteImage(idV: number, filename: string) {
      // Extract just the filename from URL like "/uploads/abc123_img.jpg"
      const ime = filename.split('/').pop();
      
      if (!ime) {
        console.error('Invalid filename');
        return;
      }
      
      this.vikendicaServis.obrisiSliku(idV, ime).subscribe({
        next: (response) => {
          console.log(response); // "Slika uspesno obrisana."
          // Refresh the image list
          this.loadImages(idV);
        },
        error: (err) => {
          console.error('Error deleting image:', err);
        }
      });
    }
}
