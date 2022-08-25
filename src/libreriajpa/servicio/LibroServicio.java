package libreriajpa.servicio;

import java.util.List;
import javax.persistence.NoResultException;
import libreriajpa.entidades.Autor;
import libreriajpa.entidades.Editorial;
import libreriajpa.entidades.Libro;
import libreriajpa.persistencia.LibroDAO;

public class LibroServicio {

    private LibroDAO libroDAO;

    public LibroServicio() {
        this.libroDAO = new LibroDAO();
    }

    /**
     * Este metodo permite registrar un libro en la base de datos
     * @param titulo titulo del libro
     * @param anio anio en el cual se creo el libro
     * @param ejemplares ejemplares totales que se posee del libro
     * @param autor autor del libro
     * @param editorial editorial del libro
     * @throws Exception
     */
    public void crearLibro(String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {
        try {

            validar(titulo, anio, ejemplares, autor, editorial);

            if (libroDAO.buscarLibroPorTitulo(titulo) != null) {
                throw new Exception("Ya existe un libro con el titulo " + titulo + ".");
            }

        } catch (NoResultException nre) {
        } catch (Exception e) {
            throw e;
        }

        try {
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroDAO.guardarLibro(libro);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo permite actualizar un libro registrado en la base de datos
     *
     * @param tituloModificar titulo que sera modificado
     * @param titulo titulo con el cual se modificara el libro
     * @param anio anio actualizado en el cual se creo el libro
     * @param ejemplares ejemplares totales actualizados que se poseen del libro
     * @param ejemplaresPrestados ejemplares del libro que han sido prestados
     * @param ejemplaresRestantes ejemplares del libro que todavia no se han
     * prestado
     * @param autor autor actualizado del libro
     * @param editorial editorial actualizada del libro
     * @throws Exception
     */
    public void actualizarLibro(String tituloModificar, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial) throws Exception {
        try {

            validar(titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);

            Libro libro = libroDAO.buscarLibroPorTitulo(tituloModificar);

            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroDAO.actualizarLibro(libro);

        } catch (NoResultException nre) {
            throw new Exception("No existe un libro con el titulo " + tituloModificar + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve un libro segun su titulo
     *
     * @param titulo titulo con el cual se buscara el libro en la base de datos
     * @return libro registrado en la base de datos
     * @throws Exception
     */
    public Libro obtenerLibroPorTitulo(String titulo) throws Exception {
        try {

            if (titulo == null || titulo.trim().isEmpty()) {
                throw new Exception("El titulo del libro no puede estar vacio.");
            }

            return libroDAO.buscarLibroPorTitulo(titulo);

        } catch (NoResultException nre) {
            throw new Exception("No existe un libro con el titulo " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo elimina un libro de la base de datos segun su titulo
     *
     * @param titulo titulo del libro con el cual se buscara y eliminara de la base de datos
     * @throws Exception
     */
    public void eliminarLibroPorTitulo(String titulo) throws Exception {
        try {

            if (titulo == null || titulo.trim().isEmpty()) {
                throw new Exception("El titulo no puede estar vacio.");
            }

            Libro libro = libroDAO.buscarLibroPorTitulo(titulo);

            libroDAO.eliminarLibro(libro.getIsbn());

        } catch (NoResultException nre) {
            throw new Exception("No existe un libro con el titulo " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Esteme metodo elimina los libros registrados en la base de datos segun el nombre del autor
     * @param nombreAutor nombre del autor
     * @throws Exception 
     */
    public void eliminarLibrosAutor(String nombreAutor) throws Exception {
        try {
            List<Libro> libros = libroDAO.buscarLibrosPorAutor(nombreAutor);

            for (Libro libro : libros) {
                libroDAO.eliminarLibro(libro.getIsbn());
            }
        } catch (NoResultException nre) {
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Este metodo elimina los lobros registrados en la base de datos segun el nombre de la editorial
     * @param nombreEditorial nombre de la editorial
     * @throws Exception 
     */
    public void eliminarLibrosEditorial(String nombreEditorial) throws Exception {
        try {
            List<Libro> libros = libroDAO.buscarLibrosPorEditorial(nombreEditorial);

            for (Libro libro : libros) {
                libroDAO.eliminarLibro(libro.getIsbn());
            }
        } catch (NoResultException nre) {
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo ayuda a realizar un prestamo modificando la cantidad de
     * ejemplares prestados y ejemplares restantes de un libro
     *
     * @param libro libro con el cual se realizara un prestamo
     * @throws Exception
     */
    public void libroPrestamo(Libro libro) throws Exception {
        try {
            // corroboro que haya libros para prestar
            if (libro.getEjemplaresRestantes() == 0) {
                throw new Exception("Ya no hay mas ejemplares para prestar del libro " + libro.getTitulo() + ".");
            }

            libro.setEjemplaresPrestados((libro.getEjemplaresPrestados() + 1));
            libro.setEjemplaresRestantes((libro.getEjemplaresRestantes() - 1));

            libroDAO.actualizarLibro(libro);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo ayuda a devolver un libro con el cual fue prestado
     * modificando los ejemplres prestados y ejemplares restantes de un libro
     *
     * @param libro libro el cual sera devuelto
     * @throws Exception
     */
    public void libroDevolucion(Libro libro) throws Exception {
        try {

            // corroboro que no haya libros para devolver
            if (libro.getEjemplares() == libro.getEjemplaresRestantes()) {
                throw new Exception("Ya no hay mas ejemplares por devolver del libro " + libro.getTitulo() + ".");
            }

            libro.setEjemplaresPrestados((libro.getEjemplaresPrestados() - 1));
            libro.setEjemplaresRestantes((libro.getEjemplaresRestantes() + 1));

            libroDAO.actualizarLibro(libro);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve todos los libros registrados en la base de datos
     *
     * @return libros registrados en la base de datos
     * @throws Exception
     */
    public List<Libro> listarLibros() throws Exception {

        if (libroDAO.listarLibros().isEmpty()) {
            throw new Exception("No existen libros registrados.");
        }

        return libroDAO.listarLibros();
    }

    public void validar(String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial) throws Exception {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El titulo del libro no puede estar vacio.");
        }

        if (anio <= 0) {
            throw new Exception("El anio no puede ser menor a cero.");
        }

        if (anio.toString().trim().isEmpty()) {
            throw new Exception("Anio no valido.");
        }

        if (2022 < anio) {
            throw new Exception("El anio no puede ser mayor al anio actual.");
        }

        if (ejemplares < 0) {
            throw new Exception("La cantidad de ejemplares no puede ser menor a cero o negativa.");
        }

        if (ejemplares == null) {
            throw new Exception("La cantidad de ejemplares no puede ser nula o vacia.");
        }

        if (ejemplares.toString().trim().isEmpty()) {
            throw new Exception("Numero de ejemplares no valido.");
        }

        if (ejemplaresPrestados == null) {
            throw new Exception("La cantidad de ejemplares prestados no puede ser nula o vacia.");
        }

        if (ejemplaresPrestados.toString().trim().isEmpty()) {
            throw new Exception("Numero de ejemplares prestados no valido.");
        }

        if (ejemplaresRestantes == null) {
            throw new Exception("La cantidad de ejemplares restantes no puede ser nula o vacia.");
        }

        if (ejemplaresRestantes.toString().trim().isEmpty()) {
            throw new Exception("Numero de ejemplares restantes no valido.");
        }

        if (ejemplares != ejemplaresPrestados + ejemplaresRestantes && ejemplaresPrestados + ejemplaresRestantes != 0) {
            throw new Exception("Ah ingresado una cantidad de ejemplares restantes o prestados incorrectamente.");
        }

        if (ejemplaresPrestados == 0 && ejemplares != ejemplaresRestantes) {
            throw new Exception("Ah ingresado una cantidad invalida de ejemplares restantes.");
        }

        if (ejemplaresRestantes == 0 && ejemplares != ejemplaresPrestados) {
            throw new Exception("Ah ingresado una cantidad invalida de ejemplares prestados.");
        }

        if (autor == null) {
            throw new Exception("El autor no puede ser nulo.");
        }

        if (editorial == null) {
            throw new Exception("La editorial no puede ser nula.");
        }

    }

    public void validar(String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El titulo del libro no puede estar vacio.");
        }

        if (anio <= 0) {
            throw new Exception("El anio no puede ser menor a cero.");
        }

        if (anio.toString().trim().isEmpty()) {
            throw new Exception("Anio no valido.");
        }

        if (2022 < anio) {
            throw new Exception("El anio no puede ser mayor al anio actual.");
        }

        if (ejemplares < 0) {
            throw new Exception("La cantidad de ejemplares no puede ser menor a cero o negativa.");
        }

        if (ejemplares == null) {
            throw new Exception("La cantidad de ejemplares no puede ser nula o vacia.");
        }

        if (ejemplares.toString().trim().isEmpty()) {
            throw new Exception("Numero de ejemplares no valido.");
        }

        if (autor == null) {
            throw new Exception("El autor no puede ser nulo.");
        }

        if (editorial == null) {
            throw new Exception("La editorial no puede ser nula.");
        }

    }

}
