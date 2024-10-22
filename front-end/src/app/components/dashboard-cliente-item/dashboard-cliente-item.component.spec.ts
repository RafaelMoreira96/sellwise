import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardClienteItemComponent } from './dashboard-cliente-item.component';

describe('DashboardClienteItemComponent', () => {
  let component: DashboardClienteItemComponent;
  let fixture: ComponentFixture<DashboardClienteItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardClienteItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardClienteItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
