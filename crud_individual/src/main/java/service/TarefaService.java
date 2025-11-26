package service;


import model.Tarefa;
import repository.TarefaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
* Serviço que contém a lógica do sistema. Usa o repository fornecido pelo Main.
*/
public class TarefaService {
private final TarefaRepository repo;


public TarefaService(TarefaRepository repo) {
this.repo = repo;
}


public Tarefa cadastrar(String titulo, String descricao, LocalDate dataConclusao) {
if (titulo == null || titulo.trim().isEmpty()) {
throw new IllegalArgumentException("Título inválido");
}
if (dataConclusao == null) {
throw new IllegalArgumentException("Data de conclusão inválida");
}
Tarefa t = new Tarefa(0, titulo.trim(), descricao == null ? "" : descricao, dataConclusao);
return repo.save(t);
}


public List<Tarefa> listar() {
return repo.findAll();
}


public Optional<Tarefa> buscar(int id) {
return repo.findById(id);
}


public boolean atualizar(int id, String titulo, String descricao, LocalDate dataConclusao) {
Optional<Tarefa> op = repo.findById(id);
if (op.isEmpty()) return false;
Tarefa t = op.get();
if (titulo != null && !titulo.isBlank()) t.setTitulo(titulo.trim());
if (descricao != null) t.setDescricao(descricao);
if (dataConclusao != null) t.setDataConclusao(dataConclusao);
repo.save(t);
return true;
}


public boolean remover(int id) {
return repo.delete(id);
}
}