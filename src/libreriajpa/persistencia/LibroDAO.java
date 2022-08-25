package libreriajpa.persistencia;

import java.util.List;
import libreriajpa.entidades.Libro;

public class LibroDAO extends DAO<Libro> {
    
    public void guardarLibro(Libro libro) {
        super.guardar(libro);
    }
    
    public void actualizarLibro(Libro libro) {
        super.editar(libro);
    }
    
    public Libro buscarLibroPorIsbn(Long isbn) {
        conectar();
        Libro libro = em.find(Libro.class, isbn);
        desconectar();
        return libro;
    }
    
    public Libro buscarLibroPorTitulo(String titulo) {
        conectar();
        Libro libro = (Libro) em.createQuery("SELECT l FROM Libro l WHERE l.titulo LIKE :titulo")
                .setParameter("titulo", titulo).getSingleResult();
        desconectar();
        return libro;
    }
    
    public void eliminarLibro(Long isbn) {
        Libro libro = buscarLibroPorIsbn(isbn);
        super.eliminar(libro);
    }
    
    public List<Libro> listarLibros() {
        conectar();
        List<Libro> libros = (List<Libro>) em.createQuery("SELECT l FROM Libro l").getResultList();
        desconectar();
        return libros;
    }
    
    public List<Libro> buscarLibrosPorAutor(String nombreAutor) {
        conectar();
        List<Libro> libros =  (List<Libro>) em.createQuery("SELECT l FROM Libro l WHERE l.autor.nombre LIKE :nombreAutor")
                .setParameter("nombreAutor", nombreAutor).getResultList();
        desconectar();
        return libros;
    }
    
    public List<Libro> buscarLibrosPorEditorial(String nombreEditorial) {
        conectar();
        List<Libro> libros =  (List<Libro>) em.createQuery("SELECT l FROM Libro l WHERE l.editorial.nombre LIKE :nombreEditorial")
                .setParameter("nombreEditorial", nombreEditorial).getResultList();
        desconectar();
        return libros;
    }
    
}
