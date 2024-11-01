import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemClienteTableComponent } from './item-cliente-table.component';

describe('ItemClienteTableComponent', () => {
  let component: ItemClienteTableComponent;
  let fixture: ComponentFixture<ItemClienteTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ItemClienteTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemClienteTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
