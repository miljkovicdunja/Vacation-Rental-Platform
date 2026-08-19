import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Korisnik } from '../models/Korisnik';

@Injectable({
  providedIn: 'root'
})
export class KorisnikService {

  constructor() { }
  url = 'http://localhost:8080/korisnici';

  http = inject(HttpClient)

  login(username: string, password: string, tip: string) {
    const data = {
      kor_ime: username,
      lozinka: password,
      tip: tip,
    };
    return this.http.post<Korisnik>(`${this.url}/login`, data);
  }
  azurirajKorisnika(korisnik: any, file?: File){
  const formData = new FormData();
  
  // Append korisnik as JSON
  formData.append('korisnik', new Blob([JSON.stringify(korisnik)], 
    { type: 'application/json' }));
  
  // Append image if provided
  if (file) {
    formData.append('slika', file);
  }
  
  return this.http.post<number>('http://localhost:8080/korisnici/azuriraj', formData);
}
  
 promenaLozinke(kor_ime: string, novaLozinka: string) {
  return this.http.post(
    `${this.url}/promenaLozinke/${kor_ime}?novaLozinka=${encodeURIComponent(novaLozinka)}`,
    {}, // POST mora imati body, može biti prazan
    { responseType: 'text' } // ako backend vraća plain text
  );
}
  dohvatiLozinku(korisnicko_ime: string) {
  return this.http.get(
    `${this.url}/dohvatiLozinku/${korisnicko_ime}`,
    { responseType: 'text' } // hash se vraća kao plain text
  );
}
  dodajKorisnika(korisnik:Korisnik){
    return this.http.post<number>(`${this.url}/dodajKorisnika`,korisnik);
  }
  
  dodajKorisnikaMultipart(korisnik: any, file?: File) {
    const formData = new FormData();
    
    // Append korisnik as JSON
    formData.append('korisnik', new Blob([JSON.stringify(korisnik)], 
      { type: 'application/json' }));
    
    // Append image if provided
    if (file) {
      formData.append('slika', file);
    }
    
    return this.http.post<number>(`${this.url}/dodajKorisnikaMultipart`, formData);
  }
  brojTurista(){
    return this.http.get<number>(`${this.url}/brojTurista`);
  }
  brojVlasnika(){
    return this.http.get<number>(`${this.url}/brojVlasnika`);
  }
  sviKorisnici(){
    return this.http.get<Korisnik[]>(`${this.url}/sviKorisnici`);
  }
  obrisiKorisnika(korisnik:Korisnik){
    return this.http.post<number>(`${this.url}/obrisiKorisnika`,korisnik);
  }
  deaktivirajKorisnika(korisnik:Korisnik){
    return this.http.post<number>(`${this.url}/deaktivirajKorisnika`,korisnik)
  }
  korisniciNaCekanju(){
    return this.http.get<Korisnik[]>(`${this.url}/korisniciNaCekanju`);
  }
  prihvatiKorisnika(korisnik:Korisnik){
    return this.http.post<number>(`${this.url}/prihvatiKorisnika`,korisnik);
  }
  odbijKorisnika(korisnik:Korisnik){
    return this.http.post<number>(`${this.url}/odbijKorisnika`,korisnik);
  }
  
  loadProfilnaSlika(kor_ime: string) {
    return this.http.get(`${this.url}/profilnaSlika/${kor_ime}`, 
      { responseType: 'text' });
  }
  
  dohvatiKorisnika(kor_ime: string) {
    return this.http.get<Korisnik>(`${this.url}/dohvatiKorisnika/${kor_ime}`);
  }
}
