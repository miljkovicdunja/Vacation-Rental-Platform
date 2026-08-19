import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Vikendice } from '../models/Vikendice';
import { Form } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class VikendiceService {

  constructor() { }

   url = 'http://localhost:8080/vikendice';
  
  http = inject(HttpClient)
  brojVikendica(){
    return this.http.get<number>(`${this.url}/brojVikendica`);
  }
  sveVikendice(){
    return this.http.get<Vikendice[]>(`${this.url}/sveVikendice`);
  }
  sortirajOpadajuce(){
    return this.http.get<Vikendice[]>(`${this.url}/sortiraneOpadajuce`);
  }
  sortirajRastuce(){
    return this.http.get<Vikendice[]>(`${this.url}/sortiraneRastuce`);
  }
  sortirajMestoOpadajuce(){
    return this.http.get<Vikendice[]>(`${this.url}/sortiraneMestoOpadajuce`);
  }
  sortirajMestoRastuce(){
    return this.http.get<Vikendice[]>(`${this.url}/sortiraneMestoRastuce`);
  }
  nadjiVikendicuPoNazivu(naziv:string){
    return this.http.get<Vikendice[]>(`${this.url}/nadjiPoNazivu/${naziv}`);
  }
  nadjiVikendicuPoMestu(mesto:string){
    return this.http.get<Vikendice[]>(`${this.url}/nadjiPoMestu/${mesto}`);
  }
  nadjiVikendicuPoNazivuIMestu(naziv:string,mesto:string){
    return this.http.get<Vikendice[]>(`${this.url}/nadjiPoNazivuIMestu/${naziv}/${mesto}`);
  }
  vikendiceVlasnika(kor_ime:string){
    return this.http.get<Vikendice[]>(`${this.url}/vikendiceVlasnika/${kor_ime}`);
  }
  urediVikendicu(vikendica: any, slike: File[], postojeceSlike: string[] = []) {
    const formData = new FormData();
    formData.append('vikendica', new Blob([JSON.stringify(vikendica)], { type: 'application/json' }));

    // Send existing image paths to preserve them
    if (postojeceSlike && postojeceSlike.length > 0) {
      formData.append('postojeceSlike', JSON.stringify(postojeceSlike));
    }

    // Send new files
    for (let slika of slike) {
      formData.append('slike', slika);
    }

    return this.http.post('http://localhost:8080/vikendice/urediVikendicu', formData);
  }
  getSlikeVikendice(idV: number){
  return this.http.get<string[]>(`http://localhost:8080/vikendice/slike/${idV}`);
  }
  obrisiSliku(idV: number, ime: string) {
  return this.http.delete(`http://localhost:8080/vikendice/slike/${idV}/${ime}`, 
    { responseType: 'text' });
  }
  obrisiVikendicu(vikendica:Vikendice){
    return this.http.post<number>(`${this.url}/obrisiVikendicu`, vikendica);
  }
  dodajVikendicu(vikendica: any, slike: File[]) {
    const formData = new FormData();
    
    // Don't include slike in the vikendica object
    const { slike: _, ...vikendicaBezSlika } = vikendica;
    formData.append('vikendica', new Blob([JSON.stringify(vikendicaBezSlika)], { type: 'application/json' }));

    // Send files
    for (let slika of slike) {
      formData.append('slike', slika);
    }

    return this.http.post<number>(`${this.url}/dodajVikendicu`, formData);
  }
  nadjiVikendicuPoId(idV:number){
    return this.http.get<Vikendice>(`${this.url}/nadjiPoId/${idV}`);
  }
}
