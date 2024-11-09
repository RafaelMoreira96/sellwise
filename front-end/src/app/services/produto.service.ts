import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private baseUrl = 'http://localhost:8080/produto';
  private produtosSubject = new BehaviorSubject<any[]>([]);
  produtos$ = this.produtosSubject.asObservable();
  
  private produtosInativosSubject = new BehaviorSubject<any[]>([]);
  produtosInativos$ = this.produtosInativosSubject.asObservable();

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }

  getAllInactiveProducts(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/inativos`);
  }

  getProductById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createProduct(product: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, product);
  }

  updateProduct(id: number, product: Object): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      // Após a exclusão, recarregue a lista de produtos
      tap(() => this.getAllProducts())
    );
  }

  reactiveProduct(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/reativar/${id}`, null).pipe(
      // Após a reativação, recarregue a lista de produtos inativos
      tap(() => this.getAllInactiveProducts())
    );
  }

  loadProducts() {
    this.getAllProducts().subscribe(produtos => {
      this.produtosSubject.next(produtos);
    });
  }

  loadInactiveProducts() {
    this.getAllInactiveProducts().subscribe(produtosInativos => {
      this.produtosInativosSubject.next(produtosInativos);
    });
  }
}
