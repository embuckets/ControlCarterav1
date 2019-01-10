/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import exceptions.AsociacionARegistroInexistenteException;
import exceptions.BusquedaException;
import exceptions.CreacionCarteraException;
import exceptions.DatosInvalidosException;
import exceptions.DatosVaciosException;
import exceptions.DetenerCarteraException;
import exceptions.NoExisteCarteraException;
import exceptions.RegistroDuplicadoException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.BaseDeDatos;
import utils.ParametrosAsegurado;

/**
 *
 * @author emilio
 */
public class ControlCarteraTest {

    private ControlCartera controlCarteraTested;

    public ControlCarteraTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        controlCarteraTested = ControlCartera.getInstance();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ControlCartera.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ControlCartera result = ControlCartera.getInstance();
        assertEquals(controlCarteraTested, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardarTelefonoRepetido() {
        System.out.println("== TEST == guardar telefono repetido");
        try {
            controlCarteraTested.conectarACartera();
            Telefono telefono = new Telefono(1, "5521195514");
            controlCarteraTested.guardar(telefono, telefono.getClass());
            controlCarteraTested.guardar(telefono, telefono.getClass()); //debe ser unico
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
            BaseDeDatos.printSQLException(ex);
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardarTelefonoInvalido() {
        System.out.println("== TEST == guardar telefono invalido");
        try {
            controlCarteraTested.conectarACartera();
            Telefono telefono = new Telefono(1, null);
            controlCarteraTested.guardar(telefono, telefono.getClass());
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
            BaseDeDatos.printSQLException(ex);
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardarDomicilioDuplicado() {
        System.out.println("== TEST == guardar domicilio duplicado");
        try {
            controlCarteraTested.conectarACartera();
            Domicilio domicilio = new Domicilio(1, "Aniceto Ortega", "1330", "casa", "03100", "del valle", "Benito Juarez", "Ciudad de Mexico");
            controlCarteraTested.guardar(domicilio, domicilio.getClass());
            controlCarteraTested.guardar(domicilio, domicilio.getClass());
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
            BaseDeDatos.printSQLException(ex);
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testBuscarTelefonoExistente() {

        System.out.println("buscarTelefonoExistente");
        try {
            controlCarteraTested.conectarACartera();
//            Telefono telefono = new Telefono(2, "5521195514");
            Set<Telefono> telefonos = controlCarteraTested.buscar(2, Telefono.class);
            for (Iterator<Telefono> it = telefonos.iterator(); it.hasNext();) {
                Telefono telefono = (Telefono) it.next();
                System.out.println("Telefono: " + telefono.getTelefono() + ",id: " + telefono.getAseguradoId());
            }
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
            BaseDeDatos.printSQLException(ex);
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (BusquedaException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardarVariosAsegurados() {
        System.out.println("guardarVariosAsegurados");
        try {
            controlCarteraTested.conectarACartera();
            Asegurado asegurado = new Asegurado("Emilio", "Hernández", "Segovia");
            Asegurado asegurado1 = new Asegurado("Daniel", "Hernández", "Segovia");
            Asegurado asegurado2 = new Asegurado("Gabriel", "Segovia", "Hernandez");
            controlCarteraTested.guardar(asegurado, asegurado.getClass());
            controlCarteraTested.guardar(asegurado1, asegurado.getClass());
            controlCarteraTested.guardar(asegurado2, asegurado.getClass());
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardarAsegurado() {
        System.out.println("guardar Asegurado");
        try {
            controlCarteraTested.conectarACartera();
            Asegurado asegurado = new Asegurado("Emilio", "Hernández", "Segovia");
            controlCarteraTested.guardar(asegurado, asegurado.getClass());
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of guardar method, of class ControlCartera.
     */
    @Test
    public void testGuardarTransaccion() {
        System.out.println("== TEST == guardar transaccion");
        try {
            String nombre = "nuevo";
            String paterno = "nuevo";
            String materno = "Perez";
            controlCarteraTested.conectarACartera();
            controlCarteraTested.empezarTransaccion();
            // guarda asegurado nuevo y obten su id
            Asegurado asegurado = new Asegurado(nombre, paterno, materno);
            controlCarteraTested.guardar(asegurado, asegurado.getClass());
            ParametrosAsegurado params = new ParametrosAsegurado();
            params.putNombre(nombre);
            params.putApellidoPaterno(paterno);
            params.putApellidoMaterno(materno);
            Set<Asegurado> asegurados = controlCarteraTested.buscar(params, Asegurado.class);
            int id = asegurados.stream().filter(a
                    -> nombre.equalsIgnoreCase(a.getNombre()) && paterno.equalsIgnoreCase(a.getApellidoPaterno().get()) && materno.equalsIgnoreCase(a.getApellidoMaterno().get())).findAny().get().getId();

            //guarda domicilio, telefonos, email
            Domicilio domicilio = new Domicilio(id, "Aniceto Ortega", "1330", "casa", "03100", "del valle", "Benito Juarez", "Ciudad de Mexico");
            controlCarteraTested.guardar(domicilio, domicilio.getClass());
            Telefono telefono = new Telefono(id, "5521195514");
            controlCarteraTested.guardar(telefono, telefono.getClass());
            Email email = new Email(id, "fulano@correo.com");
            controlCarteraTested.guardar(email, email.getClass());

            //commit
            controlCarteraTested.commit();
            controlCarteraTested.terminarTransaccion();
            controlCarteraTested.detenerCartera();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                controlCarteraTested.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (RegistroDuplicadoException ex) {
            ex.printStackTrace();
            try {
                controlCarteraTested.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        } catch (AsociacionARegistroInexistenteException ex) {
            ex.printStackTrace();
            try {
                controlCarteraTested.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        } catch (DatosVaciosException ex) {
            ex.printStackTrace();
            try {
                controlCarteraTested.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        } catch (DatosInvalidosException ex) {
            ex.printStackTrace();
            try {
                controlCarteraTested.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        } catch (BusquedaException ex) {
            ex.printStackTrace();
            try {
                controlCarteraTested.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void testBuscarAseguradoPorId() {
        try {
            controlCarteraTested.conectarACartera();
            Set<Asegurado> asegurados = controlCarteraTested.buscar(2, Asegurado.class);
            asegurados.stream().forEach((a) -> System.out.println(a.getId() + ": " + a.getNombre()));
            controlCarteraTested.detenerCartera();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (BusquedaException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            BaseDeDatos.printSQLException(ex);
        }
    }

    /**
     * Test of read method, of class ControlCartera.
     */
    @Test
    public void testReadPorParametros() {
        System.out.println("read");
        try {
            String nombre = "emilio";
            String paterno = "hernández";
            String materno = "segovia";
            controlCarteraTested.conectarACartera();
            ParametrosAsegurado params = new ParametrosAsegurado();
            params.putNombre(nombre);
            params.putApellidoPaterno(paterno);
            params.putApellidoMaterno(materno);

            Set<Asegurado> asegurados = controlCarteraTested.buscar(params, Asegurado.class);
            int id = asegurados.stream().filter(a
                    -> nombre.equalsIgnoreCase(a.getNombre()) && paterno.equalsIgnoreCase(a.getApellidoPaterno().get()) && materno.equalsIgnoreCase(a.getApellidoMaterno().get())).findAny().get().getId();
            asegurados.forEach((a) -> {
                System.out.print(a.getId() + ": ");
                System.out.println(a);
            });
            System.out.println("id: " + id);
            controlCarteraTested.detenerCartera();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (BusquedaException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of detenerCartera method, of class ControlCartera.
     */
    @Test
    public void testDetenerCartera() {
        System.out.println("detenerBaseDeDatos");
        try {
            controlCarteraTested.detenerCartera();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of conectarACartera method, of class ControlCartera.
     */
    @Test
    public void testConectarACartera() {
        System.out.println("conectarABaseDeDatos");
        try {
            controlCarteraTested.conectarACartera();
            controlCarteraTested.detenerCartera();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Test of buscarTodos method, of class ControlCartera.
     */
    @Test
    public void testBuscarTodos() {
        System.out.println("buscarTodos");
        try {
            controlCarteraTested.conectarACartera();
            Set<Asegurado> asegurados = controlCarteraTested.buscarTodos(Asegurado.class);
            System.out.println(asegurados.toString());
            controlCarteraTested.detenerCartera();
        } catch (NoExisteCarteraException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Test of crearCartera method, of class ControlCartera.
     */
    @Test
    public void testCrearCartera() {
        System.out.println("crearCartera");
        try {
            controlCarteraTested.crearCartera();
            controlCarteraTested.detenerCartera();
        } catch (CreacionCarteraException ex) {
            ex.printStackTrace();
        } catch (DetenerCarteraException ex) {
            ex.printStackTrace();
        }
    }

}
