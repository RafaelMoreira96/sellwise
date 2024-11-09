import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdutoInativoListComponent } from './produto-inativo-list.component';

describe('ProdutoInativoListComponent', () => {
  let component: ProdutoInativoListComponent;
  let fixture: ComponentFixture<ProdutoInativoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProdutoInativoListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProdutoInativoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
