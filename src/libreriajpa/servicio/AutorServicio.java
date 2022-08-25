package libreriajpa.servicio;

import java.util.List;
import javax.persistence.NoResultException;
import libreriajpa.entidades.Autor;
import libreriajpa.persistencia.AutorDAO;

public class AutorServicio {

    private AutorDAO autorDAO;

    public AutorServicio() {
        this.autorDAO = new AutorDAO();
    }

    /**
     * Este metodo permite registrar un autor en la base de datos
     * @param nombre nombre del autor
     * @throws Exception 
     */
    public void crearAutor(String nombre) throws Exception {

        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre del autor no puede estar vacio o nulo.");
            }

            if (autorDAO.buscarAutorPorNombre(nombre) != null) {
                throw new Exception("Ya existe un autor con el nombre " + nombre + ".");
            }
        } catch (NoResultException nre) {
        } catch (Exception e) {
            throw e;
        }

        try {
            Autor autor = new Autor();
            autor.setNombre(nombre);

            autorDAO.guardarAutor(autor);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo permite actualizar los datos de un autor registrado en la base de datos
     * @param nombreModificar nombre que del autor que sera modificado
     * @param nombreAcualizado nombre actualizado del autor
     * @throws Exception 
     */
    public void actualizarAutor(String nombreModificar, String nombreAcualizado) throws Exception {

        if (nombreModificar == null || nombreModificar.trim().isEmpty()) {
            throw new Exception("El nombre a modificar del autor no puede estar vacio o nulo.");
        }

        if (nombreAcualizado == null || nombreAcualizado.trim().isEmpty()) {
            throw new Exception("El nombre actual del autor no puede estar vacio o nulo.");
        }

        try {
            Autor autor = autorDAO.buscarAutorPorNombre(nombreModificar);
            autor.setNombre(nombreAcualizado);

            autorDAO.actualizarAutor(autor);
        } catch (NoResultException nre) {
            throw new Exception("No existe un autor con el nombre " + nombreModificar + ".");
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo permite eliminar un autor segun su identificador
     * @param id identificador del autor que sera eliminado de la base de datos
     * @throws Exception 
     */
    public void eliminarAutor(String id) throws Exception {
        try {

            if (id == null || id.trim().isEmpty()) {
                throw new Exception("El id del autor no puede estar vacio o nulo.");
            }

            Autor autor = autorDAO.buscarAutorPorId(id);

            autorDAO.eliminarAutor(autor.getId());

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo permite eliminar un autor segun su nombre
     * @param nombre nombre del autor que sera eliminado de la base de datos
     * @throws Exception 
     */
    public void eliminarAutorPorNombre(String nombre) throws Exception {
        try {

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre del autor no puede estar vacio o nulo.");
            }

            Autor autor = autorDAO.buscarAutorPorNombre(nombre);

            autorDAO.eliminarAutor(autor.getId());

        } catch (NoResultException e) {
            throw new Exception("No existe un autor con el nombre " + nombre + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve un autor segun su nombre
     * @param nombre nombre del autor
     * @return autor registrado en la base de datos
     * @throws Exception 
     */
    public Autor obtenerAutorPorNombre(String nombre) throws Exception {
        try {

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre no puede estar vacio o nulo.");
            }

            Autor autor = autorDAO.buscarAutorPorNombre(nombre);
            return autor;

        } catch (NoResultException e) {
            throw new Exception("No existe un autor con el nombre " + nombre + ".");
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo devuelve todos los autores registrados en la base de datos
     * @return autores registrados en la base de datos
     * @throws Exception 
     */
    public List<Autor> listarAutores() throws Exception {
        try {

            if (autorDAO.listarAutores().isEmpty()) {
                throw new Exception("No existen autores registrados.");
            }

            List<Autor> autores = autorDAO.listarAutores();

            return autores;

        } catch (Exception e) {
            throw e;
        }
    }

}
