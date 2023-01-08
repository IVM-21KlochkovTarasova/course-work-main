import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OkInformComponent } from './ok-inform.component';

describe('OkInformComponent', () => {
  let component: OkInformComponent;
  let fixture: ComponentFixture<OkInformComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OkInformComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OkInformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
