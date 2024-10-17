import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormaPagamentoService {
  private baseUrl = 'http://localhost:8080/formaPagamento';
  
  constructor(private http: HttpClient) { }

  getAllFormasPagamento(): any {
    return this.http.get<any>(this.baseUrl);
  }

  getFormaPagamentoById(id: number): any {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createFormaPagamento(formaPagamento: Object): any {
    return this.http.post(`${this.baseUrl}`, formaPagamento);
  }

  updateFormaPagamento(id: number, formaPagamento: Object): any {
    return this.http.put(`${this.baseUrl}/${id}`, formaPagamento);
  }

  deleteFormaPagamento(id: number): any {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
