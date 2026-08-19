import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoVikendicaComponent } from './info-vikendica.component';

describe('InfoVikendicaComponent', () => {
  let component: InfoVikendicaComponent;
  let fixture: ComponentFixture<InfoVikendicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoVikendicaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoVikendicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
