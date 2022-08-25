package libreriajpa.persistencia;

import java.util.List;
import libreriajpa.entidades.Editorial;


public class EditorialDAO extends DAO<Editorial> {
    
    public void guardarEditorial(Editorial editorial) {
        super.guardar(editorial);
    }
    
    public void actualizarEditorial(Editorial editorial) {
        super.editar(editorial);
    }
    
    public Editorial buscarEditorialPorId(String id) {
        conectar();
        Editorial editorial = em.find(Editorial.class, id);
        desconectar();
        return editorial;
    }
    
    public Editorial buscarEditorialPorNombre(String nombre) {
        conectar();
        Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre")
                .setParameter("nombre", nombre).getSingleResult();
        desconectar();
        return editorial;
    }
    
    public List<Editorial> listarEditoriales() {
        conectar();
        List<Editorial> editoriales = (List<Editorial>) em.createQuery("SELECT e FROM Editorial e").getResultList();
        desconectar();
        return editoriales;
    }
    
    public void eliminarEditorial(String id) {
        Editorial editorial = buscarEditorialPorId(id);
        super.eliminar(editorial);
    }
    
}
