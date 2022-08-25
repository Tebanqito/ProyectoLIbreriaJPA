package libreriajpa.servicio;

import java.util.List;
import javax.persistence.NoResultException;
import libreriajpa.entidades.Editorial;
import libreriajpa.persistencia.EditorialDAO;

public class EditorialServicio {

    private EditorialDAO editorialDAO;

    public EditorialServicio() {
        this.editorialDAO = new EditorialDAO();
    }

    /**
     * Este metodo permite registrar una editorial en la base de datos
     * @param nombre nombre de la editorial
     * @throws Exception 
     */
    public void crearEditorial(String nombre) throws Exception {
        try {

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre de la editorial no puede estar vacio o nulo.");
            }

            if (editorialDAO.buscarEditorialPorNombre(nombre) != null) {
                throw new Exception("Ya existe una editorial con el nombre " + nombre + ".");
            }

        } catch (NoResultException nre) {
        } catch (Exception e) {
            throw e;
        }
        
        try {
            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);

            editorialDAO.guardarEditorial(editorial);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo permite actualizar una editorial registrada en la base de datos
     * @param nombreModificar nombre de la editorial que sera modificada
     * @param nombreActualizado nombre actualizado de la editorial
     * @throws Exception 
     */
    public void actualizarEditorial(String nombreModificar, String nombreActualizado) throws Exception {
        try {
            if (nombreActualizado == null || nombreActualizado.trim().isEmpty()) {
                throw new Exception("El nombre actual no puede estar vacio o nulo.");
            }

            if (nombreModificar == null || nombreModificar.trim().isEmpty()) {
                throw new Exception("El nombre a modificar no puede estar vacio o nulo.");
            }

            Editorial editorial = editorialDAO.buscarEditorialPorNombre(nombreModificar);
            editorial.setNombre(nombreActualizado);

            editorialDAO.actualizarEditorial(editorial);

        } catch (NoResultException nre) {
            throw new Exception("No existe una editorial con el nombre " + nombreModificar + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo permite eliminar una editorial registrada en la base de datos segun su nombre
     * @param nombre nombre de la editorial que sera eliminada de la base de datos
     * @throws Exception 
     */
    public void eliminarEditorialPorNombre(String nombre) throws Exception {
        try {

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre no puede estar vacio o nulo.");
            }

            Editorial editorial = editorialDAO.buscarEditorialPorNombre(nombre);
            editorialDAO.eliminarEditorial(editorial.getId());

        } catch (NoResultException nre) {
            throw new Exception("No existe una editorial con el nombre " + nombre + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve una editorial registrada en la base de datos segun su nombre
     * @param nombre nombre de la editorial
     * @return editorial registrada en la base de datos
     * @throws Exception 
     */
    public Editorial obtenerEditorialPorNombre(String nombre) throws Exception {
        try {

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("El nombre no puede estar vacio o nulo.");
            }

            Editorial editorial = editorialDAO.buscarEditorialPorNombre(nombre);

            return editorial;

        } catch (NoResultException nre) {
            throw new Exception("No existe una editorial con el nombre " + nombre + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve todas las editoriales registradas en la base de datos
     * @return editoriales registradas en la base de datos
     * @throws Exception 
     */
    public List<Editorial> listarEditoriales() throws Exception {
        try {

            if (editorialDAO.listarEditoriales().isEmpty()) {
                throw new Exception("No existen editoriales registradas.");
            }

            return editorialDAO.listarEditoriales();

        } catch (Exception e) {
            throw e;
        }
    }

}
