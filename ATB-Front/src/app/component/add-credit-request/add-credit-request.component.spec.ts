import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCreditRequestComponent } from './add-credit-request.component';

describe('AddCreditRequestComponent', () => {
  let component: AddCreditRequestComponent;
  let fixture: ComponentFixture<AddCreditRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddCreditRequestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddCreditRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
