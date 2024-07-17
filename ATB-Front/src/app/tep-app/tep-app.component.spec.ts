import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TepAppComponent } from './tep-app.component';

describe('TepAppComponent', () => {
  let component: TepAppComponent;
  let fixture: ComponentFixture<TepAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TepAppComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TepAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
