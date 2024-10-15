import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FornecedorService {
  private baseUrl = 'http://localhost:8080/fornecedor';

  constructor(private http: HttpClient) { }

  getAllFornecedores(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }

  getFornecedorById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createFornecedor(fornecedor: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, fornecedor);
  }

  updateFornecedor(id: number, fornecedor: Object): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, fornecedor);
  }

  deleteFornecedor(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
