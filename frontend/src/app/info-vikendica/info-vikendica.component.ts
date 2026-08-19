import { Component, inject } from '@angular/core';
import { Vikendice } from '../models/Vikendice';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-info-vikendica',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './info-vikendica.component.html',
  styleUrl: './info-vikendica.component.css'
})
export class InfoVikendicaComponent {
  vikendica:Vikendice=new Vikendice();
  private router=inject(Router);
  slike: string[] = [];
  baseUrl = 'http://localhost:8080';


     ngOnInit(): void {
      let x = localStorage.getItem("vikendicaDetalji");
      if (x != null) {
        this.vikendica = JSON.parse(x);
        this.ucitajSlike();
      }
    }

    ucitajSlike() {
      // Parse images from string or array
      if (this.vikendica.slike) {
        if (typeof this.vikendica.slike === 'string') {
          const paths = (this.vikendica.slike as any).split(',').filter((s: string) => s.trim());
          this.slike = paths.map((path: string) => {
            const cleanPath = path.trim();
            if (cleanPath.startsWith('http')) {
              return cleanPath;
            }
            const relativePath = cleanPath.startsWith('/') ? cleanPath.substring(1) : cleanPath;
            const fullPath = relativePath.startsWith('uploads/') ? relativePath : `uploads/${relativePath}`;
            return `${this.baseUrl}/${fullPath}`;
          });
          console.log('Parsed slike:', this.slike); // Debug log
        } else if (Array.isArray(this.vikendica.slike)) {
          // If slike is already an array
          this.slike = (this.vikendica.slike as any).map((path: string) => {
            const cleanPath = path.trim();
            if (cleanPath.startsWith('http')) {
              return cleanPath;
            }
            const relativePath = cleanPath.startsWith('/') ? cleanPath.substring(1) : cleanPath;
            const fullPath = relativePath.startsWith('uploads/') ? relativePath : `uploads/${relativePath}`;
            return `${this.baseUrl}/${fullPath}`;
          });
          console.log('Parsed slike array:', this.slike); // Debug log
        }
      }
      console.log('Raw vikendica.slike:', this.vikendica.slike); // Debug log
    }

    otvoriSliku(url: string) {
      // Open image in new tab for full size view
      window.open(url, '_blank');
    }

    
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
      this.router.navigate(['/turistaRezervacije']);
    }
}
