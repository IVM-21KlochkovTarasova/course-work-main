import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalAdminTestComponent } from './final-admin-test.component';

describe('FinalAdminTestComponent', () => {
  let component: FinalAdminTestComponent;
  let fixture: ComponentFixture<FinalAdminTestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalAdminTestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalAdminTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
