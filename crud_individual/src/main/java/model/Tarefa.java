package model;


import java.time.LocalDate;


/**
* Model que representa uma tarefa.
*/
public class Tarefa {
private int id;
private String titulo;
private String descricao;
private LocalDate dataConclusao;


public Tarefa() {}


public Tarefa(int id, String titulo, String descricao, LocalDate dataConclusao) {
this.id = id;
this.titulo = titulo;
this.descricao = descricao;
this.dataConclusao = dataConclusao;
}


public int getId() { return id; }
public void setId(int id) { this.id = id; }


public String getTitulo() { return titulo; }
public void setTitulo(String titulo) { this.titulo = titulo; }


public String getDescricao() { return descricao; }
public void setDescricao(String descricao) { this.descricao = descricao; }


public LocalDate getDataConclusao() { return dataConclusao; }
public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }


@Override
public String toString() {
return String.format("Tarefa{id=%d, titulo='%s', descricao='%s', dataConclusao=%s}",
id, titulo, descricao == null ? "" : descricao, dataConclusao);
}
}