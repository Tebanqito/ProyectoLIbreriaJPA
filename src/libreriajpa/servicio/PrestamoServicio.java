package libreriajpa.servicio;

import java.util.List;
import java.util.Date;
import javax.persistence.NoResultException;
import libreriajpa.entidades.Cliente;
import libreriajpa.entidades.Libro;
import libreriajpa.entidades.Prestamo;
import libreriajpa.persistencia.PrestamoDAO;

public class PrestamoServicio {

    private PrestamoDAO prestamoDAO;
    private ClienteServicio clienteServicio;
    private LibroServicio libroServicio;

    public PrestamoServicio() {
        this.prestamoDAO = new PrestamoDAO();
        this.clienteServicio = new ClienteServicio();
        this.libroServicio = new LibroServicio();
    }

    /**
     * Este metodo prepara la realizacion de prestamo que se complementara con
     * el metodo de la clase LibroServicio llamado libroPrestamo
     * 
     * @param fechaPrestamo fecha en la cual se realiza el prestamo
     * @param libro libro que sera prestado
     * @param cliente cliente quien realiza el prestamo
     * @throws Exception
     */
    public void prepararPrestamo(Date fechaPrestamo, Libro libro, Cliente cliente) throws Exception {
        try {
            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorClienteLibro(cliente.getDocumento(), libro.getTitulo());
            if (!prestamos.isEmpty()) {
                // prestamos.get(prestamos.size() - 1) es el ultimo objeto de la lista llamada prestamos
                if (prestamos.get(prestamos.size() - 1).getFechaDevolucion() == null && libro.getTitulo().equalsIgnoreCase(prestamos.get(prestamos.size() - 1).getLibro().getTitulo())) {
                    throw new Exception("El cliente " + cliente.getNombre() + " " + cliente.getApellido() + " ya realizo un prestamo del libro "
                            + libro.getTitulo() + " que aun no ha devuelto.");
                }
            }

            validar(fechaPrestamo, libro, cliente);

            Prestamo prestamo = new Prestamo();
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setLibro(libro);
            prestamo.setCliente(cliente);

            prestamoDAO.guardarPrestamo(prestamo);

        } catch (NoResultException nre) {
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo prepara la devolucion del libro el cual fue prestado y se
     * complementa con el metodo de la clase LibroServicio llamado libroDevolucion
     * 
     * @param titulo titulo del libro con el cual busco el libro que fue prestado
     * @param documentoCliente documento del cliente que realizo el prestamo del libro
     * @throws Exception
     */
    public void devolucionPrestamo(String titulo, Long documentoCliente) throws Exception {
        try {

            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);

            if (prestamos.get(prestamos.size() - 1).getFechaDevolucion() != null) {
                throw new Exception("El libro " + titulo + " ya ha sido devuelto por el cliente " + documentoCliente);
            }

            if (titulo == null || titulo.trim().isEmpty()) {
                throw new Exception("El titulo del libro no puede ser nulo o estar vacio.");
            }

            if (documentoCliente <= 0 || documentoCliente == null) {
                throw new Exception("El documento no puede ser menor a cero o ser nulo.");
            }

            Prestamo prestamo = prestamos.get(prestamos.size() - 1);
            prestamo.setFechaDevolucion(new Date());

            prestamoDAO.actualizarPrestamo(prestamo);

        } catch (NoResultException nre) {
            throw new Exception("No existe un prestamo realizado por el cliente " + documentoCliente + " con el libro " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo permite actualizar los datos del prestamo de un libro que
     * todavia no ha sido devuelto
     *
     * @param documentoCliente documento del cliente que realizo el prestamo
     * @param titulo titulo del libro que fue parte del prestamo
     * @param fechaPrestamo fecha actualizada en la cual se realizo el prestamo
     * @param libro libro actualizado que sera parte del prestamo
     * @param cliente cliente actualizado que sera parte del prestamo
     * @throws Exception
     */
    public void actualizarPrestamoSinDevolucion(Long documentoCliente, String titulo, Date fechaPrestamo, Libro libro, Cliente cliente) throws Exception {

        validar(fechaPrestamo, libro, cliente);

        try {
            clienteServicio.obtenerClientePorDocumento(documentoCliente);
        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el documento: " + documentoCliente + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            libroServicio.obtenerLibroPorTitulo(titulo);
        } catch (NoResultException e) {
            throw new Exception("No existe un libro con el titulo " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
        } catch (NoResultException e) {
            throw new Exception("No existe un prestamo realizado por el cliente " + documentoCliente + " con el libro " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El titulo no puede estar vacio o nulo.");
        }

        try {
            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
            Prestamo prestamo = obtenerPrestamoSinDevolucion(prestamos);

            if (prestamo.getFechaDevolucion() != null) {
                throw new Exception("No existe un prestamo sin devolucion del libro " + prestamo.getLibro().getTitulo());
            }

            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setLibro(libro);
            prestamo.setCliente(cliente);

            prestamoDAO.actualizarPrestamo(prestamo);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo permite actualizar los datos del prestamo de un libro que ya
     * ha sido devuelto
     *
     * @param documentoCliente documento del cliente que realizo el prestamo
     * @param titulo titulo del libro que fue parte del prestamo
     * @param fechaPrestamo fecha actualizada en la cual se realizo el prestamo
     * @param fechaDevolucion fecha actualizada en la cual se finalizo el
     * prestamo
     * @param libro libro actualizado que sera parte del prestamo
     * @param cliente cliente actualizado que sera parte del prestamo
     * @throws Exception
     */
    public void actualizarPrestamoConDevolucion(Long documentoCliente, String titulo, Date fechaPrestamo, Date fechaDevolucion, Libro libro, Cliente cliente) throws Exception {

        validar(fechaPrestamo, fechaDevolucion, libro, cliente);

        try {
            clienteServicio.obtenerClientePorDocumento(documentoCliente);
        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el documento: " + documentoCliente + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            libroServicio.obtenerLibroPorTitulo(titulo);
        } catch (NoResultException e) {
            throw new Exception("No existe un libro con el titulo " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
        } catch (NoResultException e) {
            throw new Exception("No existe un prestamo realizado por el cliente " + documentoCliente + " con el libro " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El titulo no puede estar vacio o nulo.");
        }
        try {
            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
            Prestamo prestamo = prestamos.get(prestamos.size() - 1);

            if (prestamo.getFechaDevolucion() == null) {
                throw new Exception("El prestamo del libro " + prestamo.getLibro().getTitulo() + " que realizo el cliente "
                        + prestamo.getCliente().getNombre() + " " + prestamo.getCliente().getApellido() + " aun no ha sido devuelto.");
            }

            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);
            prestamo.setLibro(libro);
            prestamo.setCliente(cliente);

            prestamoDAO.actualizarPrestamo(prestamo);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Este metodo retorna un prestamo que aun no ha sido devuelto, de otra forma retornara un valor nulo
     * 
     * @param prestamos lista de prestamos que sera iterada para buscar un prestamo que tenga el valor nulo en su atributo fechaDevolucion
     * @return un objeto prestamo con el valor nulo en su atributo fechaDevolucion o valor nulo
     * @throws Exception 
     */
    public Prestamo obtenerPrestamoSinDevolucion(List<Prestamo> prestamos) throws Exception {
        
        if (!prestamos.isEmpty()) {
            for (Prestamo prestamo : prestamos) {
                if (prestamo.getFechaDevolucion() == null) {
                    return prestamo;
                }
            }
        } else {
            throw new Exception("No hay prestamos registrados.");
        }
        
        return null;
        
    }

    /**
     * Este metodo elimina un prestamo segun el cliente que lo realizo y el
     * libro con el cual se realizo
     *
     * @param documentoCliente documento del cliente quien realizo el prestamo
     * @param titulo titulo del libro con el cual se ralizo el prestamo
     * @throws Exception
     */
    public void eliminarPrestamoPorClienteLibro(Long documentoCliente, String titulo) throws Exception {

        try {
            clienteServicio.obtenerClientePorDocumento(documentoCliente);
        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el documento: " + documentoCliente + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            libroServicio.obtenerLibroPorTitulo(titulo);
        } catch (NoResultException e) {
            throw new Exception("No existe un libro con el titulo " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
        } catch (NoResultException e) {
            throw new Exception("No existe un prestamo realizado por el cliente " + documentoCliente + " con el libro " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("El titulo no puede estar vacio o nulo.");
        }

        List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
        Prestamo prestamo = prestamos.get(prestamos.size() - 1);
        prestamoDAO.eliminarPrestamo(prestamo.getId());

    }

    /**
     * Este metodo elimina los prestamos registrados en la base de datos segun
     * el titulo del libro que tengan los prestamos
     *
     * @param titulo titulo del libro
     * @throws Exception
     */
    public void eliminarPrestamoLibro(String titulo) throws Exception {
        try {

            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorLibro(titulo);

            for (Prestamo prestamo : prestamos) {
                prestamoDAO.eliminarPrestamo(prestamo.getId());
            }

        } catch (NoResultException e) {
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo elimina los prestamos registrados en la base de datos segun
     * los autores que tengan los libros del prestamo
     *
     * @param nombreAutor nombre del autor
     * @throws Exception
     */
    public void eliminarPrestamoAutor(String nombreAutor) throws Exception {
        try {

            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorAutor(nombreAutor);

            for (Prestamo prestamo : prestamos) {
                prestamoDAO.eliminarPrestamo(prestamo.getId());
            }

        } catch (NoResultException e) {
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo elimina los prestamos registrados en la base de datos segun
     * la editorial que tengan los libros del prestamo
     *
     * @param nombreEditorial nombre de la editorial
     * @throws Exception
     */
    public void eliminarPrestamoEditorial(String nombreEditorial) throws Exception {
        try {

            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorEditorial(nombreEditorial);

            for (Prestamo prestamo : prestamos) {
                prestamoDAO.eliminarPrestamo(prestamo.getId());
            }

        } catch (NoResultException e) {
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelce un prestamo segun el cliente que lo realizo y el
     * libro con el cual se realizo
     *
     * @param documentoCliente documento del cliente quien realizo el prestamo
     * @param titulo titulo del libro con el cual se ralizo el prestamo
     * @return prestamo registrado en la base de datos
     * @throws Exception
     */
    public Prestamo obtenerPrestamoPorClienteLibro(Long documentoCliente, String titulo) throws Exception {

        try {
            clienteServicio.obtenerClientePorDocumento(documentoCliente);
        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el documento " + documentoCliente + ".");
        } catch (Exception e) {
            throw e;
        }

        try {
            List<Prestamo> prestamos = prestamoDAO.buscarPrestamoPorClienteLibro(documentoCliente, titulo);
            Prestamo prestamo = prestamos.get(prestamos.size() - 1);
            return prestamo;
        } catch (NoResultException e) {
            throw new Exception("No existe un prestamo realizado por el cliente " + documentoCliente + " con el libro " + titulo + ".");
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo devuelve todos los prestamos registrados en la base de datos
     *
     * @return prestamos registrados en la base de datos
     * @throws Exception
     */
    public List<Prestamo> listarPrestamos() throws Exception {
        try {

            if (prestamoDAO.listarPrestamos().isEmpty()) {
                throw new Exception("No existen prestamos registrados.");
            }

            return prestamoDAO.listarPrestamos();

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve todos los prestamos que realizo un cliente
     *
     * @param documento documento del cliente que ha realizado prestamos
     * @return prestamos del cliente registrados en la base de datos
     * @throws Exception
     */
    public List<Prestamo> listarPrestamosPorCliente(Long documento) throws Exception {
        try {

            if (prestamoDAO.listarPrestamos().isEmpty()) {
                throw new Exception("No existen prestamos registrados.");
            }

            if (documento == null || documento <= 0) {
                throw new Exception("El numero de docuemnto no puede ser menor a cero o nulo.");
            }

            return prestamoDAO.listarPrestamosPorCliente(documento);

        } catch (Exception e) {
            throw e;
        }
    }

    public void validar(Date fechaPrestamo, Date fechaDevolucion, Libro libro, Cliente cliente) throws Exception {
        try {

            if (fechaPrestamo == null) {
                throw new Exception("La fecha del prestamo no puede ser nula.");
            }

            if (fechaDevolucion == null) {
                throw new Exception("La fecha de devolucion no puede ser nula.");
            }

            if (fechaPrestamo.after(fechaDevolucion)) {
                throw new Exception("La fecha del prestamo no puede ser posterior a la fecha de devolucion.");
            }

            if (libro == null) {
                throw new Exception("El libro no puede ser nulo.");
            }

            if (cliente == null) {
                throw new Exception("El cliente no puede ser nulo.");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public void validar(Date fechaPrestamo, Libro libro, Cliente cliente) throws Exception {
        try {

            if (fechaPrestamo == null) {
                throw new Exception("La fecha del prestamo no puede ser nula.");
            }

            if (fechaPrestamo.after(new Date())) {
                throw new Exception("La fecha del prestamo no puede ser posterior a la fecha actual.");
            }

            if (libro == null) {
                throw new Exception("El libro no puede ser nulo.");
            }

            if (cliente == null) {
                throw new Exception("El cliente no puede ser nulo.");
            }

        } catch (Exception e) {
            throw e;
        }
    }

}
