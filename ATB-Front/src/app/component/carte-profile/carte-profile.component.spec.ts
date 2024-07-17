import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarteProfileComponent } from './carte-profile.component';

describe('CarteProfileComponent', () => {
  let component: CarteProfileComponent;
  let fixture: ComponentFixture<CarteProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CarteProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarteProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
