import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card-fast-info',
  templateUrl: './card-fast-info.component.html',
  styleUrl: './card-fast-info.component.css'
})
export class CardFastInfoComponent {
  @Input() icon: string = 'fas fa-user-check';
  @Input() category: string = 'Category';     
  @Input() title: number = 0;               
}
