import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardFastInfoComponent } from './card-fast-info.component';

describe('CardFastInfoComponent', () => {
  let component: CardFastInfoComponent;
  let fixture: ComponentFixture<CardFastInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CardFastInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardFastInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
