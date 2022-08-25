package libreriajpa.servicio;

import java.util.List;
import javax.persistence.NoResultException;
import libreriajpa.entidades.Cliente;
import libreriajpa.persistencia.ClienteDAO;

public class ClienteServicio {

    private ClienteDAO clienteDAO;

    public ClienteServicio() {
        this.clienteDAO = new ClienteDAO();
    }

    /**
     * Este metodo permite registar un cliente en la base de datos
     * @param documento docuemtno del cliente
     * @param nombre nombre del cliente
     * @param apellido apellido del cliente
     * @param telefono numero de telefono del cliente
     * @throws Exception 
     */
    public void crearCliente(Long documento, String nombre, String apellido, String telefono) throws Exception {
        try {

            validar(documento, nombre, apellido, telefono);

            if (clienteDAO.buscarClientePorDocumento(documento) != null) {
                throw new Exception("Ya existe un cliente con el mismo numero de documento " + documento + ".");
            }

            if (clienteDAO.buscarClientePorTelefono(telefono) != null) {
                throw new Exception("Ya existe un cliente con el mismo numero de telefono " + telefono + ".");
            }

        } catch (NoResultException e) {
        } catch (Exception e) {
            throw e;
        }
        try {
            Cliente cliente = new Cliente();
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);

            clienteDAO.guardarCliente(cliente);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo pemrite actualizar los datos de un cliente registrado en la base de datos
     * @param documentoModificar documento del cliente que sera modificado
     * @param documentoModificado docuemnto del cliente ya actualizado
     * @param nombre nombre del cleinte actualizado
     * @param apellido apellido del cliente actualizado
     * @param telefono numero de telefono del cliente actualizado
     * @throws Exception 
     */
    public void actualizarClientePorDocumento(Long documentoModificar, Long documentoModificado, String nombre, String apellido, String telefono) throws Exception {

        validar(documentoModificado, nombre, apellido, telefono);

        if (documentoModificar == null || documentoModificar <= 0) {
            throw new Exception("El documento a modificar no puede ser menor a cero o nulo.");
        }

        try {
            if (clienteDAO.buscarClientePorDocumento(documentoModificado) != null) {
                throw new Exception("Ya existe un cliente con el mismo numero de documento " + documentoModificado + ".");
            }
        } catch (NoResultException e) {
        } catch (Exception e) {
            throw e;
        }

        try {
            if (clienteDAO.buscarClientePorTelefono(telefono) != null) {
                throw new Exception("Ya existe un cliente con el mismo numero de telefono " + telefono + ".");
            }
        } catch (NoResultException e) {
        } catch (Exception e) {
            throw e;
        }

        try {
            Cliente cliente = clienteDAO.buscarClientePorDocumento(documentoModificar);
            cliente.setDocumento(documentoModificado);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);

            clienteDAO.actualizarCliente(cliente);
        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el numero de documento " + documentoModificar);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Este metodo permite eliminaro un cliente de la base de datos segun su numero de documento
     * @param documento documento del cliente que sera eliminado de la base de datos
     * @throws Exception 
     */
    public void eliminarClientePorDocumento(Long documento) throws Exception {
        try {

            if (documento == null || documento <= 0) {
                throw new Exception("El documento no puede ser menor a cero o nulo.");
            }

            Cliente cliente = clienteDAO.buscarClientePorDocumento(documento);
            clienteDAO.eliminarCliente(cliente.getId());

        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el documento " + documento + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve un cliente registrado en la base de datos segun su numero de documento
     * @param documento documento del cliente
     * @return cliente registrado en la base de datos
     * @throws Exception 
     */
    public Cliente obtenerClientePorDocumento(Long documento) throws Exception {
        try {

            if (documento == null || documento <= 0) {
                throw new Exception("El documento no puede ser menor a cero o nulo.");
            }

            return clienteDAO.buscarClientePorDocumento(documento);

        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el documento " + documento + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelve un cliente registrado en la base dedatos segun su numero de telefono
     * @param telefono numero de telefono del cliente
     * @return cliente registrado en la base de datos
     * @throws Exception 
     */
    public Cliente obtenerClientePorTelefono(String telefono) throws Exception {
        try {

            if (telefono.trim().isEmpty() || telefono == null) {
                throw new Exception("Numero de telefono no valido.");
            }

            return clienteDAO.buscarClientePorTelefono(telefono);

        } catch (NoResultException e) {
            throw new Exception("No existe un cliente con el numero de telefono " + telefono + ".");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Este metodo devuelde todos los clientes registrados en la base de datos
     * @return clientes registrados en la base de datos
     * @throws Exception 
     */
    public List<Cliente> listarClientes() throws Exception {
        try {

            if (clienteDAO.listarClientes().isEmpty()) {
                throw new Exception("No existen clientes registrados.");
            }

            return clienteDAO.listarClientes();

        } catch (Exception e) {
            throw e;
        }
    }

    public void validar(Long documento, String nombre, String apellido, String telefono) throws Exception {

        if (documento <= 0) {
            throw new Exception("El documento no puede ser menor o igual a cero.");
        }

        if (documento == null) {
            throw new Exception("El documento no puede ser nulo.");
        }

        if (documento.toString().trim().isEmpty()) {
            throw new Exception("Numero de documento no valido.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del cliente no puede estar vacio o nulo.");
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("El apellido del cliente no puede estar vacio o nulo.");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new Exception("El telefono del cliente no puede estar vacio o nulo.");
        }

    }

}
