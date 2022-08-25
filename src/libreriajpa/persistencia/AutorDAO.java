package libreriajpa.persistencia;

import java.util.List;
import libreriajpa.entidades.Autor;

public class AutorDAO extends DAO<Autor> {
    
    public void guardarAutor(Autor autor) {
        super.guardar(autor);
    }
    
    public void actualizarAutor(Autor autor) {
        super.editar(autor);
    }
    
    public Autor buscarAutorPorId(String id) {
        conectar();
        Autor autor = em.find(Autor.class, id);
        desconectar();
        return autor;
    }
    
    public Autor buscarAutorPorNombre(String nombre) {
        conectar();
        Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre").setParameter("nombre", nombre).getSingleResult();
        desconectar();
        return autor;
    }
    
    public void eliminarAutor(String id) {
        Autor autor = buscarAutorPorId(id);
        super.eliminar(autor);
    }
    
    public List<Autor> listarAutores() {
        conectar();
        List<Autor> autores = (List<Autor>) em.createQuery("SELECT a FROM Autor a").getResultList();
        desconectar();
        return autores;
    }
    
}
