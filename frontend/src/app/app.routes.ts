import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TuristaComponent } from './turista/turista.component';
import { VlasnikComponent } from './vlasnik/vlasnik.component';
import { AdministratorComponent } from './administrator/administrator.component';
import { LoginAdministratorComponent } from './login-administrator/login-administrator.component';
import { AzuriranjeComponent } from './azuriranje/azuriranje.component';
import { PromenaLozinkeComponent } from './promena-lozinke/promena-lozinke.component';
import { PrijaviSeComponent } from './prijavi-se/prijavi-se.component';
import { NeregistrovaniComponent } from './neregistrovani/neregistrovani.component';
import { TuristaVikendiceComponent } from './turista-vikendice/turista-vikendice.component';
import { InfoVikendicaComponent } from './info-vikendica/info-vikendica.component';
import { VlasnikVikendiceComponent } from './vlasnik-vikendice/vlasnik-vikendice.component';
import { AzurirajVikendicuComponent } from './azuriraj-vikendicu/azuriraj-vikendicu.component';
import { NovaVikendicaComponent } from './nova-vikendica/nova-vikendica.component';
import { RezervacijeTuristaComponent } from './rezervacije-turista/rezervacije-turista.component';
import { NovaRezervacijaComponent } from './nova-rezervacija/nova-rezervacija.component';
import { RezervacijeVlasnikComponent } from './rezervacije-vlasnik/rezervacije-vlasnik.component';

export const routes: Routes = [
    {path:"", component:NeregistrovaniComponent},
    {path:"login",component:LoginComponent},
    {path:"turista", component:TuristaComponent},
    {path:"vlasnik", component:VlasnikComponent},
    {path:"LoginAdministrator", component:LoginAdministratorComponent},
    {path:"administrator", component:AdministratorComponent},
    {path:"azuriranje",component:AzuriranjeComponent},
    {path:"promenaLozinke",component:PromenaLozinkeComponent},
    {path:"prijaviSe",component:PrijaviSeComponent},
    {path:"turistaVikendice", component:TuristaVikendiceComponent},
    {path:"vikendiceVlasnika", component:VlasnikVikendiceComponent},
    {path:"azurirajVikendicu", component:AzurirajVikendicuComponent},
    {path:"novaVikendica",component:NovaVikendicaComponent},
    {path:"infoVikendica",component:InfoVikendicaComponent},
    {path:"turistaRezervacije", component:RezervacijeTuristaComponent},
    {path:"novaRezervacija",component:NovaRezervacijaComponent},
    {path:"rezervacijeVlasnika", component: RezervacijeVlasnikComponent}
];
