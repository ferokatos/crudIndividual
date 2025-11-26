package repository;


import model.Tarefa;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
* Repositório em memória que funciona como "banco de dados".
* Mantém um Map<Integer, Tarefa> como dicionário único.
*/
public class TarefaRepository {
private final Map<Integer, Tarefa> armazenamento = new ConcurrentHashMap<>();
private int nextId = 1;


public List<Tarefa> findAll() {
return new ArrayList<>(armazenamento.values());
}


public Optional<Tarefa> findById(int id) {
return Optional.ofNullable(armazenamento.get(id));
}


public Tarefa save(Tarefa tarefa) {
if (tarefa.getId() == 0) {
tarefa.setId(nextId++);
}
armazenamento.put(tarefa.getId(), tarefa);
return tarefa;
}


public boolean delete(int id) {
return armazenamento.remove(id) != null;
}


public void clearAll() {
armazenamento.clear();
nextId = 1;
}
}