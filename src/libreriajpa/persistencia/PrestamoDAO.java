package libreriajpa.persistencia;

import java.util.List;
import libreriajpa.entidades.Prestamo;

public class PrestamoDAO extends DAO<Prestamo> {
    
     public void guardarPrestamo(Prestamo prestamo) {
        super.guardar(prestamo);
    }
    
    public void actualizarPrestamo(Prestamo prestamo) {
        super.editar(prestamo);
    }
    
    public Prestamo buscarPrestamoPorId(String id) {
        conectar();
        Prestamo prestamo = em.find(Prestamo.class, id);
        desconectar();
        return prestamo;
    }
    
    public List<Prestamo> buscarPrestamoPorClienteLibro(Long documentoCliente, String titulo) {
        conectar();
        List<Prestamo> prestamos = (List<Prestamo>) em.createQuery("SELECT p FROM Prestamo p WHERE p.cliente.documento = :documentoCliente AND p.libro.titulo LIKE :titulo")
                .setParameter("documentoCliente", documentoCliente).setParameter("titulo", titulo).getResultList();
        desconectar();
        return prestamos;
    }
    
    public List<Prestamo> buscarPrestamoPorLibro(String titulo) {
        conectar();
        List<Prestamo> prestamos = (List<Prestamo>) em.createQuery("SELECT p FROM Prestamo p WHERE p.libro.titulo LIKE :titulo")
                .setParameter("titulo", titulo).getResultList();
        desconectar();
        return prestamos;
    }
    
    public List<Prestamo> buscarPrestamoPorAutor(String nombreAutor) {
        conectar();
        List<Prestamo> prestamos = (List<Prestamo>) em.createQuery("SELECT p FROM Prestamo p WHERE p.libro.autor.nombre LIKE :nombreAutor")
                .setParameter("nombreAutor", nombreAutor).getResultList();
        desconectar();
        return prestamos;
    }
    
    public List<Prestamo> buscarPrestamoPorEditorial(String nombreEditorial) {
        conectar();
        List<Prestamo> prestamos = (List<Prestamo>) em.createQuery("SELECT p FROM Prestamo p WHERE p.libro.editorial.nombre LIKE :nombreEditorial")
                .setParameter("nombreEditorial", nombreEditorial).getResultList();
        desconectar();
        return prestamos;
    }
    
    public List<Prestamo> listarPrestamos() {
        conectar();
        List<Prestamo> prestamos = (List<Prestamo>) em.createQuery("SELECT p FROM Prestamo p").getResultList();
        desconectar();
        return prestamos;
    }
    
     public List<Prestamo> listarPrestamosPorCliente(Long documento) {
        conectar();
        List<Prestamo> prestamos = (List<Prestamo>) em.createQuery("SELECT p FROM Prestamo p WHERE p.cliente.documento = :documento")
                .setParameter("documento", documento).getResultList();
        desconectar();
        return prestamos;
    }
    
    public void eliminarPrestamo(String id) {
        Prestamo prestamo = buscarPrestamoPorId(id);
        super.eliminar(prestamo);
    }
    
}
