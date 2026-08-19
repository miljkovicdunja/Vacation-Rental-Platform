import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Korisnik } from '../models/Korisnik';
import { Rezervacije } from '../models/Rezervacije';

@Injectable({
  providedIn: 'root'
})
export class RezervacijeService {

  constructor() { }
  
    url = 'http://localhost:8080/rezervacije';
  
    http = inject(HttpClient)
  brojRezervacija24h(){
    return this.http.get(`${this.url}/24h`); 
  }
  brojRezervacija7dana(){
    return this.http.get(`${this.url}/7dana`);
  }
  brojRezervacija30dana(){
    return this.http.get(`${this.url}/30dana`)
  }
  trenutneRezervacijeTuriste(korisnik:String){
    return this.http.get<Rezervacije[]>(`${this.url}/trenutne/${korisnik}`);
  }
  
  dodajRezervaciju(rezervacija: Rezervacije){
    return this.http.post<Rezervacije>(`${this.url}/dodaj`, rezervacija);
  }
  neobradjeneRezervacije(korisnik:Korisnik){
    return this.http.post<Rezervacije[]>(`${this.url}/neobradjene`,korisnik);
  }
  prihvatiRezervaciju(rezervacija:number){
    return this.http.post<number>(`${this.url}/prihvati/${rezervacija}`, {});
  }
  odbijRezervaciju(rezervacija:number, komentar:string){
    return this.http.post<number>(`${this.url}/odbij/${rezervacija}/${komentar}`, {});
  }
  otkaziRezervaciju(rezervacija:Rezervacije){
    return this.http.post<number>(`${this.url}/otkazi`,rezervacija);
  }
}
