import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-item-cliente-table',
  templateUrl: './item-cliente-table.component.html',
  styleUrl: './item-cliente-table.component.css'
})
export class ItemClienteTableComponent {
  @Input() nome_cliente: string = '';
  @Input() data_cadastro: string = '';
}
