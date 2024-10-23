import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableVendasDashboardComponent } from './table-vendas-dashboard.component';

describe('TableVendasDashboardComponent', () => {
  let component: TableVendasDashboardComponent;
  let fixture: ComponentFixture<TableVendasDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TableVendasDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableVendasDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
