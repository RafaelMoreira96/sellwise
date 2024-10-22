import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-dashboard-cliente-item',
  templateUrl: './dashboard-cliente-item.component.html',
  styleUrl: './dashboard-cliente-item.component.css'
})
export class DashboardClienteItemComponent {
  @Input() nome_cliente: string = '';
  @Input() data_cadastro: string = '';
}
