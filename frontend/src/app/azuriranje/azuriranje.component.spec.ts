import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AzuriranjeComponent } from './azuriranje.component';

describe('AzuriranjeComponent', () => {
  let component: AzuriranjeComponent;
  let fixture: ComponentFixture<AzuriranjeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AzuriranjeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AzuriranjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
