import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NovaVikendicaComponent } from './nova-vikendica.component';

describe('NovaVikendicaComponent', () => {
  let component: NovaVikendicaComponent;
  let fixture: ComponentFixture<NovaVikendicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NovaVikendicaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NovaVikendicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
