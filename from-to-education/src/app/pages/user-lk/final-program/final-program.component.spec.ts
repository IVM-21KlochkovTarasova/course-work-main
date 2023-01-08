import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalProgramComponent } from './final-program.component';

describe('FinalProgramComponent', () => {
  let component: FinalProgramComponent;
  let fixture: ComponentFixture<FinalProgramComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalProgramComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalProgramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
