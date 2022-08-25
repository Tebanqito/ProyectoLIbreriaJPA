package libreriajpa.menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import libreriajpa.servicio.MetodosLecturaServicio;

public class Menu {
    
    private MetodosLecturaServicio metodosLecturaServicio;
    private Scanner scanner;
    private int opcion;
    
    public Menu() {
        this.metodosLecturaServicio = new MetodosLecturaServicio();
        this.scanner = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n");
        this.opcion = 0;
    }
    
    public void menuPrincipal() throws Exception {
        do {
            try {
                System.out.println("ELIJA UNA OPCIÓN: ");
                System.out.println("1. CREAR AUTOR");
                System.out.println("2. CREAR EDITORIAL");
                System.out.println("3. CREAR LIBRO");
                System.out.println("4. CREAR CLIENTE");
                System.out.println("5. CREAR PRESTAMO");
                System.out.println("6. MODIFICAR AUTOR");
                System.out.println("7. MODIFICAR EDITORIAL");
                System.out.println("8. MODIFICAR LIBRO");
                System.out.println("9. MODIFICAR CLIENTE");
                System.out.println("10. MODIFICAR PRESTAMO SIN FECHA DE DEVOLUCION");
                System.out.println("11. MODIFICAR PRESTAMO CON FECHA DE DEVOLUCION");
                System.out.println("12. ELIMINAR AUTOR");
                System.out.println("13. ELIMINAR EDITORIAL");
                System.out.println("14. ELIMINAR LIBRO");
                System.out.println("15. ELIMINAR CLIENTE");
                System.out.println("16. ELIMINAR PRESTAMO");
                System.out.println("17. BUSCAR AUTOR");
                System.out.println("18. BUSCAR EDITORIAL");
                System.out.println("19. BUSCAR LIBRO");
                System.out.println("20. BUSCAR CLIENTE");
                System.out.println("21. BUSCAR PRESTAMO SEGUN EL LIBRO Y EL CLIENTE");
                System.out.println("22. BUSCAR PRESTAMOS DE UN CLIENTE");
                System.out.println("23. LISTAR AUTORES");
                System.out.println("24. LISTAR EDITORIALES");
                System.out.println("25. LISTAR LIBROS");
                System.out.println("26. LISTAR CLIENTES");
                System.out.println("27. LISTAR PRESTAMOS");
                System.out.println("28. FINALIZAR PRESTAMO");
                System.out.println("0. SALIR");

                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        metodosLecturaServicio.crearAutor();
                        break;
                    case 2:
                        metodosLecturaServicio.crearEditorial();
                        break;
                    case 3:
                        metodosLecturaServicio.crearLibro();
                        break;
                    case 4:
                        metodosLecturaServicio.crearCliente();
                        break;
                    case 5:
                        metodosLecturaServicio.crearPrestamo();
                        break;
                    case 6:
                        metodosLecturaServicio.modificarAutor();
                        break;
                    case 7:
                        metodosLecturaServicio.modificarEditorial();
                        break;
                    case 8:
                        metodosLecturaServicio.modificarLibro();
                        break;
                    case 9:
                        metodosLecturaServicio.modificarClientePorDocumento();
                        break;
                    case 10:
                        metodosLecturaServicio.modificarPrestamoSinDevolucion();
                        break;
                    case 11:
                        metodosLecturaServicio.modificarPrestamoConDevolucion();
                        break;
                    case 12:
                        metodosLecturaServicio.eliminarAutorPorNombre();
                        break;
                    case 13:
                        metodosLecturaServicio.eliminarEditorialPorNombre();
                        break;
                    case 14:
                        metodosLecturaServicio.eliminarLibroPorTitulo();
                        break;
                    case 15:
                        metodosLecturaServicio.eliminarClientePorDocumento();
                        break;
                    case 16:
                        metodosLecturaServicio.eliminarPrestamoPorClienteLibro();
                        break;
                    case 17:
                        metodosLecturaServicio.buscarAutorPorNombre();
                        break;
                    case 18:
                        metodosLecturaServicio.buscarEditorialPorNombre();
                        break;
                    case 19:
                        metodosLecturaServicio.buscarLibroPorTitulo();
                        break;
                    case 20:
                        metodosLecturaServicio.buscarClientePorDocumento();
                        break;
                    case 21:
                        metodosLecturaServicio.buscarPrestamoPorClienteLibro();
                        break;
                    case 22:
                        metodosLecturaServicio.listarPrestamosPorCliente();
                        break;
                    case 23:
                        metodosLecturaServicio.listarAutores();
                        break;
                    case 24:
                        metodosLecturaServicio.listarEditoriales();
                        break;
                    case 25:
                        metodosLecturaServicio.listarLibros();
                        break;
                    case 26:
                        metodosLecturaServicio.listarClientes();
                        break;
                    case 27:
                        metodosLecturaServicio.listarPrestamos();
                        break;
                    case 28:
                        metodosLecturaServicio.finalizarPrestamo();
                        break;
                    case 0:
                        System.out.println("*** SESION FINZALIZADA ***");
                        break;
                    default:
                        System.out.println("LA OPCIÓN INGRESADA NO ES VÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                scanner.next();
            } catch (Exception e) {
                throw e;
            }
        } while (opcion != 0);

    }
    
}
