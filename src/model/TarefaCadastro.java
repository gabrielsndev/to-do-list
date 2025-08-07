package model;

import java.util.Date;

public class TarefaCadastro {
	protected String titulo;
	protected Date data;
	protected String status;
	protected String editar;
	protected String apagar;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditar() {
		return editar;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

	public String getApagar() {
		return apagar;
	}

	public void setApagar(String apagar) {
		this.apagar = apagar;
	}

	public TarefaCadastro() {
		
	}
		
	public TarefaCadastro(String t, Date d, String s, String ed, String ap) {
		this.titulo = t;
		this.data = d;
		this.status = s;
		this.editar = ed;
		this.apagar = ap;
	}
	


	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
