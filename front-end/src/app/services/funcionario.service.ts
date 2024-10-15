import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {
  private baseUrl = 'http://localhost:8080/funcionario';

  constructor(private http: HttpClient) { }

  getAllFuncionarios(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }

  getFuncionarioById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createFuncionario(funcionario: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, funcionario);
  }

  updateFuncionario(id: number, funcionario: Object): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, funcionario);
  }

  deleteFuncionario(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
