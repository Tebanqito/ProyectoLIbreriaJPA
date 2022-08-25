package libreriajpa.servicio;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import libreriajpa.entidades.Autor;
import libreriajpa.entidades.Cliente;
import libreriajpa.entidades.Editorial;
import libreriajpa.entidades.Libro;
import libreriajpa.entidades.Prestamo;

public class MetodosLecturaServicio {

    private LibroServicio libroServicio;
    private AutorServicio autorServicio;
    private EditorialServicio editorialServicio;
    private ClienteServicio clienteServicio;
    private PrestamoServicio prestamoServicio;
    private Scanner scanner;

    public MetodosLecturaServicio() {
        this.libroServicio = new LibroServicio();
        this.autorServicio = new AutorServicio();
        this.editorialServicio = new EditorialServicio();
        this.clienteServicio = new ClienteServicio();
        this.prestamoServicio = new PrestamoServicio();
        this.scanner = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n");
    }

    // metodos de autor
    public void crearAutor() throws Exception {

        System.out.println("Ingrese el nombre del autor que desea crear: ");
        String nombreAutor = scanner.next();

        autorServicio.crearAutor(nombreAutor);
    }

    public void modificarAutor() throws Exception {

        System.out.println("Ingrese el nombre del autor que desea modificar: ");
        String nombreModificar = scanner.next();

        System.out.println("Ingrese el nombre del autor modificado: ");
        String nombreModificado = scanner.next();

        autorServicio.actualizarAutor(nombreModificar, nombreModificado);

    }

    public void eliminarAutorPorNombre() throws Exception {

        System.out.println("Ingrese el nombre del autor que desea eliminar:");
        String nombreAutor = scanner.next();

        prestamoServicio.eliminarPrestamoAutor(nombreAutor);
        libroServicio.eliminarLibrosAutor(nombreAutor);
        autorServicio.eliminarAutorPorNombre(nombreAutor);

    }

    public void buscarAutorPorNombre() throws Exception {

        System.out.println("Ingrese el nombre del autor que desea buscar: ");
        String nombreAutor = scanner.next();

        Autor autor = autorServicio.obtenerAutorPorNombre(nombreAutor);

        System.out.println(autor);

    }

    public void listarAutores() throws Exception {

        List<Autor> autores = autorServicio.listarAutores();

        for (Autor autor : autores) {
            System.out.println(autor);
        }

    }

    // metodos de editorial
    public void crearEditorial() throws Exception {

        System.out.println("Ingrese el nombre de la editorial que desea crear: ");
        String nombreEditorial = scanner.next();

        editorialServicio.crearEditorial(nombreEditorial);

    }

    public void modificarEditorial() throws Exception {

        System.out.println("Ingrese el nombre de la editorial que desea modificar: ");
        String nombreModificar = scanner.next();

        System.out.println("Ingrese le nombre modificado de la editorial: ");
        String nombreModificado = scanner.next();

        editorialServicio.actualizarEditorial(nombreModificar, nombreModificado);

    }

    public void eliminarEditorialPorNombre() throws Exception {

        System.out.println("Ingrese el nombre de la editorial que desea eliminar: ");
        String nombreEditorial = scanner.next();

        prestamoServicio.eliminarPrestamoEditorial(nombreEditorial);
        libroServicio.eliminarLibrosEditorial(nombreEditorial);
        editorialServicio.eliminarEditorialPorNombre(nombreEditorial);

    }

    public void buscarEditorialPorNombre() throws Exception {

        System.out.println("Ingrese el nombre de la editorial que desea buscar: ");
        String nombreEditorial = scanner.next();

        Editorial editorial = editorialServicio.obtenerEditorialPorNombre(nombreEditorial);
        System.out.println(editorial);

    }

    public void listarEditoriales() throws Exception {

        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        for (Editorial editorial : editoriales) {
            System.out.println(editorial);
        }

    }

    // metodos de libro
    public void crearLibro() throws Exception {

        System.out.println("Ingrese el titulo del libro que desea crear: ");
        String titulo = scanner.next();

        System.out.println("Ingrese el anio en donde se creo el libro: ");
        Integer anio = scanner.nextInt();

        System.out.println("Ingrese los ejemplares del libro: ");
        Integer ejemplares = scanner.nextInt();

        System.out.println("Ingrese el nombre del autor del libro: ");
        String nombreAutor = scanner.next();
        Autor autor = autorServicio.obtenerAutorPorNombre(nombreAutor);

        System.out.println("Ingrese la editorial del libro: ");
        String nombreEditorial = scanner.next();
        Editorial editorial = editorialServicio.obtenerEditorialPorNombre(nombreEditorial);

        libroServicio.crearLibro(titulo, anio, ejemplares, autor, editorial);

    }

    public void modificarLibro() throws Exception {

        System.out.println("Ingrese el titulo del libro que desea modificar: ");
        String tituloModificar = scanner.next();

        System.out.println("Ingrese el titulo modificado del libro: ");
        String tituloModificado = scanner.next();

        System.out.println("Ingrese el anio modificado del libro: ");
        Integer anioModificado = scanner.nextInt();

        System.out.println("Ingrese la cantidad de ejemplares modificada: ");
        Integer ejemplaresModificado = scanner.nextInt();

        System.out.println("Ingrese la cantidad de ejemplares prestados modificado: ");
        Integer ejemplaresPrestadosModificado = scanner.nextInt();

        System.out.println("Ingrese el nombre del autor modificado: ");
        String nombreAutorModificado = scanner.next();
        Autor autor = autorServicio.obtenerAutorPorNombre(nombreAutorModificado);

        System.out.println("Ingrese el nombre de la editorial modificada: ");
        String nombreEditorialModificada = scanner.next();
        Editorial editorial = editorialServicio.obtenerEditorialPorNombre(nombreEditorialModificada);

        libroServicio.actualizarLibro(tituloModificar, tituloModificado, anioModificado, ejemplaresModificado, 0, ejemplaresModificado, autor, editorial);
        
        // si la cantidad de ejemplares es mayor a cero entonces se realizaran los prestamos de ese libro modificado
        if (ejemplaresPrestadosModificado > 0) {
            for (int i = 1; i <= ejemplaresPrestadosModificado; i++) {
                System.out.println("Ingrese los datos del prestamo numero " + i + ":");
                crearPrestamoLibroModificado(tituloModificado);
            }
        }

    }

    public void eliminarLibroPorTitulo() throws Exception {

        System.out.println("Ingrese el titulo del libro que desea eliminar: ");
        String titulo = scanner.next();

        prestamoServicio.eliminarPrestamoLibro(titulo);
        libroServicio.eliminarLibroPorTitulo(titulo);

    }

    public void buscarLibroPorTitulo() throws Exception {

        System.out.println("Ingrese el titulo del libro que desea buscar: ");
        String titulo = scanner.next();

        Libro libro = libroServicio.obtenerLibroPorTitulo(titulo);

        System.out.println(libro);

    }

    public void listarLibros() throws Exception {

        List<Libro> libros = libroServicio.listarLibros();

        for (Libro libro : libros) {
            System.out.println(libro);
        }

    }

    // metodos de cliente
    public void crearCliente() throws Exception {

        System.out.println("Ingrese el documento del cliente: ");
        Long documento = scanner.nextLong();

        System.out.println("Ingrese el nombre del cliente: ");
        String nombre = scanner.next();

        System.out.println("Ingrese el apellido del cliente: ");
        String apellido = scanner.next();

        System.out.println("Ingrese el numero de telefono del cliente: ");
        String telefono = scanner.next();

        clienteServicio.crearCliente(documento, nombre, apellido, telefono);

    }

    public void modificarClientePorDocumento() throws Exception {

        System.out.println("Ingrese el documento del cliente que desea modificar: ");
        Long documentoModificar = scanner.nextLong();

        System.out.println("Ingrese el documento modificado: ");
        Long documentoModificado = scanner.nextLong();

        System.out.println("Ingrese el nombre modificado del cliente: ");
        String nombre = scanner.next();

        System.out.println("Ingrese el apellido modificado del cliente: ");
        String apellido = scanner.next();

        System.out.println("Ingrese el telefono modificado del cliente: ");
        String telefono = scanner.next();

        clienteServicio.actualizarClientePorDocumento(documentoModificar, documentoModificado, nombre, apellido, telefono);

    }

    public void eliminarClientePorDocumento() throws Exception {

        System.out.println("Ingrese el documento del cliente que desea eliminar: ");
        Long documento = scanner.nextLong();

        clienteServicio.eliminarClientePorDocumento(documento);

    }

    public void buscarClientePorDocumento() throws Exception {

        System.out.println("Ingrese el documento del cliente que desea buscar: ");
        Long documento = scanner.nextLong();

        Cliente cliente = clienteServicio.obtenerClientePorDocumento(documento);
        System.out.println(cliente);

    }

    public void listarClientes() throws Exception {

        List<Cliente> clientes = clienteServicio.listarClientes();

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }

    }

    // metodos de prestamo
    public void crearPrestamo() throws Exception {

        System.out.println("Ingrese el titulo del libro que sera prestado: ");
        String titulo = scanner.next();
        Libro libro = libroServicio.obtenerLibroPorTitulo(titulo);

        System.out.println("Ingrese el documento del cliente que realizara el prestamo: ");
        Long documento = scanner.nextLong();
        Cliente cliente = clienteServicio.obtenerClientePorDocumento(documento);

        crearPrestamo2(new Date(), libro, cliente);

    }

    public void crearPrestamoLibroModificado(String titulo) throws Exception {

        Libro libro = libroServicio.obtenerLibroPorTitulo(titulo);

        System.out.println("Ingrese el documento del cliente que realizara el prestamo del libro " + titulo + ": ");
        Long documento = scanner.nextLong();
        Cliente cliente = clienteServicio.obtenerClientePorDocumento(documento);
        
        System.out.println("Ingrese el dia en que se realizo el prestamo: ");
        int dia = scanner.nextInt();
        
        System.out.println("Ingrese el mes en que se realizo el prestamo: ");
        int mes = scanner.nextInt();
        
        System.out.println("Ingrese el anio en que se realizo el prestamo: ");
        int anio = scanner.nextInt();

        // Al anio le resto 1900 ya que la clase Date cuando quiero ingresar un anio le suma 1900 y por lo tanto al restarle 1900
        // estaria ingresando el anio correctamente.
        // Al mes le resto 1 ya que el primer mes que vendria a ser Enero empieza en cero
        crearPrestamo2(new Date(anio - 1900, mes - 1, dia), libro, cliente);

    }

    public void finalizarPrestamo() throws Exception {

        System.out.println("Ingrese el titulo del libro con el cual se relizo el prestamo: ");
        String titulo = scanner.next();

        System.out.println("Ingres el documento del cliente que relizo el prestamo: ");
        Long documento = scanner.nextLong();

        finalizarPrestamo2(titulo, documento);

    }

    public void modificarPrestamoSinDevolucion() throws Exception {
        
        System.out.println("Ingrese el documento del cliente quien realizo el prestamo:");
        Long documentoClienteAnterior = scanner.nextLong();

        System.out.println("Ingrese el titulo del libro con el cual se realizo el prestamo: ");
        String titulo = scanner.next();

        System.out.println("Ingrese el anio donde se realizo el prestamo: ");
        int anio = scanner.nextInt();

        System.out.println("Ingrese el mes donde se realizo el prestamo: ");
        int mes = scanner.nextInt();

        System.out.println("Ingrese el dia donde se realizo el prestamo: ");
        int dia = scanner.nextInt();

        Date fecha = new Date(anio - 1900, mes - 1, dia);

        System.out.println("Ingrese el titulo del libro con el cual desea modificar el prestamo: ");
        String tituloModificado = scanner.next();
        Libro libro = libroServicio.obtenerLibroPorTitulo(tituloModificado);

        System.out.println("Ingrese el documento del cliente que realizo el prestamo modificado: ");
        Long documentoCliente = scanner.nextLong();
        Cliente cliente = clienteServicio.obtenerClientePorDocumento(documentoCliente);

        prestamoServicio.actualizarPrestamoSinDevolucion(documentoClienteAnterior, titulo, fecha, libro, cliente);

    }

    public void modificarPrestamoConDevolucion() throws Exception {
        
        System.out.println("Ingrese el documento del cliente quien realizo el prestamo:");
        Long documentoClienteAnterior = scanner.nextLong();

        System.out.println("Ingrese el titulo del libro con el cual se realizo el prestamo: ");
        String titulo = scanner.next();

        System.out.println("Ingrese el anio donde se realizo el prestamo: ");
        int aniofechaPrestamo = scanner.nextInt();

        System.out.println("Ingrese el mes donde se realizo el prestamo: ");
        int mesfechaPrestamo = scanner.nextInt();

        System.out.println("Ingrese el dia donde se realizo el prestamo: ");
        int diafechaPrestamo = scanner.nextInt();

        Date fechaPrestamo = new Date(aniofechaPrestamo - 1900, mesfechaPrestamo - 1, diafechaPrestamo);

        System.out.println("Ingrese el anio donde se realizo la devolucion: ");
        int anioDevolucion = scanner.nextInt();

        System.out.println("Ingrese el mes donde se realizo la devolucion: ");
        int mesDevolucion = scanner.nextInt();

        System.out.println("Ingrese el dia donde se realizo la devolucion: ");
        int diaDevolucion = scanner.nextInt();

        Date fechaDevolucion = new Date(anioDevolucion - 1900, mesDevolucion - 1, diaDevolucion);

        System.out.println("Ingrese el titulo del libro con el cual desea modificar el prestamo: ");
        String tituloModificado = scanner.next();
        Libro libro = libroServicio.obtenerLibroPorTitulo(tituloModificado);

        System.out.println("Ingrese el documento del cliente modificado: ");
        Long documentoCliente = scanner.nextLong();
        Cliente cliente = clienteServicio.obtenerClientePorDocumento(documentoCliente);

        prestamoServicio.actualizarPrestamoConDevolucion(documentoClienteAnterior, titulo, fechaPrestamo, fechaDevolucion, libro, cliente);

    }

    public void eliminarPrestamoPorClienteLibro() throws Exception {

        System.out.println("Ingrese el documento del cliente el cual realizo el prestamo que desea eliminar: ");
        Long documento = scanner.nextLong();

        System.out.println("Ingrese el titulo del libro con el cual se realizo el prestamo que desea eliminar: ");
        String titulo = scanner.next();

        prestamoServicio.eliminarPrestamoPorClienteLibro(documento, titulo);

    }

    public void buscarPrestamoPorClienteLibro() throws Exception {

        System.out.println("Ingrese el documento del cliente que desea buscar: ");
        Long documento = scanner.nextLong();

        System.out.println("Ingrese el titulo del libro que desea buscar: ");
        String titulo = scanner.next();

        Prestamo prestamo = prestamoServicio.obtenerPrestamoPorClienteLibro(documento, titulo);
        System.out.println(prestamo);

    }

    public void listarPrestamos() throws Exception {

        List<Prestamo> prestamos = prestamoServicio.listarPrestamos();

        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo);
        }

    }

    public void listarPrestamosPorCliente() throws Exception {

        System.out.println("Ingrese el documento del cliente para saber los prestamos que ha realizado: ");
        Long documento = scanner.nextLong();

        List<Prestamo> prestamos = prestamoServicio.listarPrestamosPorCliente(documento);

        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo);
        }

    }

    // metodos auxiliares
    public static void crearPrestamo2(Date fechaPrestamo, Libro libro, Cliente cliente) throws Exception {

        PrestamoServicio prestamoServicio = new PrestamoServicio();
        LibroServicio libroServicio = new LibroServicio();

        prestamoServicio.prepararPrestamo(fechaPrestamo, libro, cliente);
        libroServicio.libroPrestamo(libro);

    }

    public static void finalizarPrestamo2(String libro, Long documentoCliente) throws Exception {

        PrestamoServicio prestamoServicio = new PrestamoServicio();
        LibroServicio libroServicio = new LibroServicio();

        prestamoServicio.devolucionPrestamo(libro, documentoCliente);

        Libro libro2 = libroServicio.obtenerLibroPorTitulo(libro);
        libroServicio.libroDevolucion(libro2);

    }

}
