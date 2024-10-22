import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendaItemListComponent } from './table-vendas-dashboard.component';

describe('VendaItemListComponent', () => {
  let component: VendaItemListComponent;
  let fixture: ComponentFixture<VendaItemListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VendaItemListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendaItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
