package libreriajpa.persistencia;

import java.util.List;
import libreriajpa.entidades.Cliente;

public class ClienteDAO extends DAO<Cliente> {
    
     public void guardarCliente(Cliente cliente) {
        super.guardar(cliente);
    }
    
    public void actualizarCliente(Cliente cliente) {
        super.editar(cliente);
    }
    
    public Cliente buscarClientePorId(String id) {
        conectar();
        Cliente cliente = em.find(Cliente.class, id);
        desconectar();
        return cliente;
    }
    
    public Cliente buscarClientePorDocumento(Long documento) {
        conectar();
        Cliente cliente = (Cliente) em.createQuery("SELECT c FROM Cliente c WHERE c.documento = :documento").setParameter("documento", documento).getSingleResult();
        desconectar();
        return cliente;
    }
    
    public Cliente buscarClientePorNombreApellido(String nombre, String apellido) {
        conectar();
        Cliente cliente = (Cliente) em.createQuery("SELECT c FROM Cliente c WHERE c.nombre = :nombre AND c.apellido LIKE :apeliido")
                .setParameter("nombre", nombre).setParameter("apellido", apellido).getSingleResult();
        desconectar();
        return cliente;
    }
    
    public Cliente buscarClientePorTelefono(String telefono) {
        conectar();
        Cliente cliente = (Cliente) em.createQuery("SELECT c FROM Cliente c WHERE c.telefono LIKE :telefono")
                .setParameter("telefono", telefono).getSingleResult();
        desconectar();
        return cliente;
    }
    
    public Cliente buscarClientePorNombreApellidoDocumento(String nombre, String apellido, Long documento) {
        conectar();
        Cliente cliente = (Cliente) em.createQuery("SELECT c FROM Cliente c WHERE c.nombre = :nombre AND c.apellido LIKE :apeliido AND c.documento = :documento")
                .setParameter("nombre", nombre).setParameter("apellido", apellido).setParameter("documento", documento).getSingleResult();
        desconectar();
        return cliente;
    }
    
    public List<Cliente> listarClientes() {
        conectar();
        List<Cliente> clientes = (List<Cliente>) em.createQuery("SELECT c FROM Cliente c").getResultList();
        desconectar();
        return clientes;
    } 
    
    public void eliminarCliente(String id) {
        Cliente cliente = buscarClientePorId(id);
        super.eliminar(cliente);
    }
    
}
