package libreriajpa.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO<T> {
    
    // el EntityManagerFactory permite crear un EntityManager
    protected final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("LibreriaJPAPU");
    // el EM permite realizar transacciones con la base de datos
    protected EntityManager em = EMF.createEntityManager();

    // CONECTAR A LA BASE DE DATOS
    protected void conectar() {
        //VERIFICO QUE NO EXISTA LA CONEXION
        if (!em.isOpen()) {
            em = EMF.createEntityManager();
        }
    }

    // DESCONECTAR DE LA BASE DE DATOS
    protected void desconectar() {

        // VERIFICO QUE EXISTA LA CONEXION
        if (em.isOpen()) {
            em.close();
        }
    }

    // INSERT INTO - PERSISTIR UN REGISTRO EN LA BASE DE DATOS
    protected void guardar(T objeto) {
        conectar(); //conecto a la base de datos
        em.getTransaction().begin(); // inicio la transaccion
        em.persist(objeto); // persisto/guardo un registro completo en la base de datos
        em.getTransaction().commit(); // finalizo la transaccion
        desconectar(); // me desconecto de la base de datos
    }

    // UPDATE - MODIFICAR DATOS DE UN REGISTRO
    protected void editar(T objeto) {
        conectar();
        em.getTransaction().begin();
        em.merge(objeto);// elimina el registro viejo, y lo reemplaza por el nuevo con sus respectivas modificaciones
        em.getTransaction().commit();
        em.close();
        desconectar();
    }

    // DELETE - BORRAR UN RESGITRO/FILA DE UNA TABLA EN LA BASE DE DATOS
    protected void eliminar(T objeto) {
        conectar();
        em.getTransaction().begin();
        T objetoARemover = em.merge(objeto); // obtengo el resgistro
        em.remove(objetoARemover); // elimino el resgistro de la base de datos
        em.getTransaction().commit();
        desconectar();
    }
 
}
