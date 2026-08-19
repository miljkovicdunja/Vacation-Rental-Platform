import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NovaRezervacijaComponent } from './nova-rezervacija.component';

describe('NovaRezervacijaComponent', () => {
  let component: NovaRezervacijaComponent;
  let fixture: ComponentFixture<NovaRezervacijaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NovaRezervacijaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NovaRezervacijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
