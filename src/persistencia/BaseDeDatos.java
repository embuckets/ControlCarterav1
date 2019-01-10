/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Asegurado;
import dominio.Dinero;
import dominio.Poliza;
import dominio.Recibo;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Configuracion;
import utils.ParametrosAsegurado;

/**
 *
 * @author emilio
 */
public class BaseDeDatos {

    private static final String FRAMEWORK = "embedded";
    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DB_NAME = "cartera";

//    private static final String ASEGURADOS_COL_NAME_ID = "aseguradoId";
//    private static final String ASEGURADOS_COL_NAME_NOMBRE = "nombre";
//    private static final String ASEGURADOS_COL_NAME_AP_PATERNO = "apPaterno";
    private static BaseDeDatos instance;
    private static Connection connection;
    private static String USER;
    private static String PASSWORD;

    private BaseDeDatos() {
        USER = Configuracion.getInstance().getDefaultBDUser();
        PASSWORD = Configuracion.getInstance().getDefaultBDPassword();
    }

    public static BaseDeDatos getInstance() {
        if (instance == null) {
            instance = new BaseDeDatos();
        }
        return instance;
    }

    public void crearBaseDeDatos() throws SQLException {
        String CREAR_TABLA_ASEGURADOS = "CREATE TABLE asegurados("
                + "aseguradoId INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "nombre varchar(50) NOT NULL,"
                + "apPaterno varchar(50) NOT NULL DEFAULT '',"
                + "apMaterno varchar(50) NOT NULL DEFAULT '',"
                + "rfc varchar(13),"
                + "nacimiento DATE,"
                + "PRIMARY KEY (aseguradoId),"
                + "CONSTRAINT UNQ_asegurado UNIQUE (nombre, apPaterno, apMaterno)"
                + ")";
        String CREAR_TABLA_TELEFONOS = "CREATE TABLE telefonos("
                + "aseguradoId INT NOT NULL REFERENCES asegurados(aseguradoId),"
                + "telefono varchar(20) NOT NULL,"
                + "CONSTRAINT UNQ_telefono UNIQUE (aseguradoId, telefono)"
                + ")";
        String CREAR_TABLA_EMAILS = "CREATE TABLE emails("
                + "aseguradoId INT NOT NULL REFERENCES asegurados(aseguradoId),"
                + "email varchar(40) NOT NULL,"
                + "CONSTRAINT UNQ_email UNIQUE (aseguradoId, email)"
                + ")";
        String CREAR_TABLA_ESTADOS = "CREATE TABLE estados("
                + "estado varchar(50) NOT NULL UNIQUE"
                + ")";
        String CREAR_TABLA_DOMICILIOS = "CREATE TABLE domicilios("
                + "aseguradoId INT NOT NULL REFERENCES asegurados(aseguradoId),"
                + "calle varchar(50) NOT NULL,"
                + "exterior varchar(50) NOT NULL,"
                + "interior varchar(20),"
                + "codPostal char(5) NOT NULL,"
                + "colonia varchar(50) NOT NULL,"
                + "delegacion varchar(50) NOT NULL,"
                + "estado varchar(50) NOT NULL REFERENCES estados(estado),"
                + "CONSTRAINT UNQ_domicilio UNIQUE (aseguradoId)"//TODO: deberia crear tabla estados?? proly yes
                + ")";
        String CREAR_TABLA_ASEGURADORAS = "CREATE TABLE aseguradoras("
                + "aseguradora varchar(20) NOT NULL,"
                + "PRIMARY KEY (aseguradora)"
                + ")";
        String CREAR_TABLA_RAMOS = "CREATE TABLE ramos("
                + "ramo varchar(30) NOT NULL,"
                + "PRIMARY KEY (ramo)"
                + ")";
        String CREAR_TABLA_POLIZAS = "CREATE TABLE polizas("
                + "polizaId INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "numero varchar(20) NOT NULL,"
                + "aseguradora varchar(20) NOT NULL REFERENCES aseguradoras(aseguradora),"
                + "asegurado INT NOT NULL REFERENCES asegurados(aseguradoId),"
                + "ramo varchar(30) NOT NULL REFERENCES ramos(ramo),"
                + "producto varchar(50) NOT NULL,"
                + "plan varchar(50) NOT NULL,"
                + "inicioVigencia DATE NOT NULL,"
                + "finVigencia DATE NOT NULL,"
                + "conductoCobro varchar(15) NOT NULL, CHECK (conductoCobro IN ('agente', 'cash', 'cat')),"
                + "formaPago varchar(20) NOT NULL, CHECK (formaPago IN ('mensual','trimestral','semestral','anual')),"
                + "prima DECIMAL(9,2) NOT NULL,"
                + "moneda varchar(10) NOT NULL,"
                + "deducible DECIMAL(9,2),"
                + "monedaDeducible varchar(10),"
                + "sumaAsegurada DECIMAL(9,2),"
                + "monedaSumaAsegurada varchar(10),"
                + "coaseguro SMALLINT,"//porcentaje
                //TODO: + "estado varchar(6) NOT NULL, CHECK(estado IN ('vigente, 'vieja'')),"
                + "comentarios varchar(70),"
                + "PRIMARY KEY (polizaId),"
                + "CONSTRAINT UNQ_poliza UNIQUE (numero, inicioVigencia),"
                + "CONSTRAINT CHK_moneda CHECK (moneda IN ('pesos','dolares','umam') AND "
                + "monedaDeducible IN ('pesos','dolares','umam') AND "
                + "monedaSumaAsegurada IN ('pesos','dolares','umam'))"
                + ")";
        String CREAR_TABLA_RECIBOS = "CREATE TABLE recibos("
                + "polizaId INT NOT NULL REFERENCES polizas(polizaId),"
                + "cubreDesde DATE NOT NULL,"
                + "cubreHasta DATE NOT NULL,"
                + "importe DECIMAL(9,2) NOT NULL,"
                + "cobranza varchar(9) NOT NULL, CHECK (cobranza IN ('pendiente','pagado')),"
                + "CONSTRAINT UNQ_recibo UNIQUE (polizaId, cubreDesde)"
                + ")";
        String CREAR_TABLA_BENEFICIARIOS = "CREATE TABLE beneficiarios("
                + "polizaId INT NOT NULL REFERENCES polizas(polizaId),"
                + "nombre varchar(50) NOT NULL,"
                + "apPaterno varchar(50) NOT NULL,"
                + "apMaterno varchar(50) NOT NULL,"
                + "nacimiento DATE NOT NULL,"
                + "CONSTRAINT UNQ_beneficiario UNIQUE (polizaId, nombre, apPaterno, apMaterno)"
                + ")";
        //TODO: TABLA DOCUMENTOS
//    private String CREAR_TABLA_DOCUMENTOS = "CREATE TABLE documentos ("
//            + ""
        String CREAR_INDEX_RECIBOS_COBRANZA = "CREATE INDEX idx_cobranza ON recibos(cobranza)";
        String CREAR_INDEX_RECIBOS_CUBRE_DESDE = "CREATE INDEX idx_cubreDede ON recibos(cubreDesde)";
        String[] INSERTS_ASEGURADORAS = {
            "INSERT INTO aseguradoras VALUES('GNP')",
            "INSERT INTO aseguradoras VALUES('AXA')",
            "INSERT INTO aseguradoras VALUES('AARCO')",
            "INSERT INTO aseguradoras VALUES('ZURICH')",
            "INSERT INTO aseguradoras VALUES('METLIFE')",
            "INSERT INTO aseguradoras VALUES('CHUBB')",
            "INSERT INTO aseguradoras VALUES('PLAN SEGURO')"};
        String[] INSERTS_RAMOS = {
            "INSERT INTO ramos VALUES('AUTOS')",
            "INSERT INTO ramos VALUES('HOGAR')",
            "INSERT INTO ramos VALUES('TRANSPORTE')",
            "INSERT INTO ramos VALUES('EMPRESARIAL')",
            "INSERT INTO ramos VALUES('RESPONSABILIDAD CIVIL')",
            "INSERT INTO ramos VALUES('GASTOS MEDICOS')",
            "INSERT INTO ramos VALUES('ACCIDENTES PERSONALES')",
            "INSERT INTO ramos VALUES('VIDA')",
            "INSERT INTO ramos VALUES('INVERSION')"};
        String [] INSERTS_ESTADOS = {
            "INSERT INTO estados VALUES('Aguascalientes')",
            "INSERT INTO estados VALUES('Baja California')",
            "INSERT INTO estados VALUES('Baja California Sur')",
            "INSERT INTO estados VALUES('Campeche')",
            "INSERT INTO estados VALUES('Chiapas')",
            "INSERT INTO estados VALUES('Chihuahua')",
            "INSERT INTO estados VALUES('Ciudad de México')",
            "INSERT INTO estados VALUES('Coahuila')",
            "INSERT INTO estados VALUES('Colima')",
            "INSERT INTO estados VALUES('Durango')",
            "INSERT INTO estados VALUES('Estado de México')",
            "INSERT INTO estados VALUES('Guanajuato')",
            "INSERT INTO estados VALUES('Guerrero')",
            "INSERT INTO estados VALUES('Hidalgo')",
            "INSERT INTO estados VALUES('Jalisco')",
            "INSERT INTO estados VALUES('Michoacán')",
            "INSERT INTO estados VALUES('Morelos')",
            "INSERT INTO estados VALUES('Nayarit')",
            "INSERT INTO estados VALUES('Nuevo León')",
            "INSERT INTO estados VALUES('Oaxaca')",
            "INSERT INTO estados VALUES('Puebla')",
            "INSERT INTO estados VALUES('Querétaro')",
            "INSERT INTO estados VALUES('Quintana Roo')",
            "INSERT INTO estados VALUES('San Luis Potosí')",
            "INSERT INTO estados VALUES('Sinaloa')",
            "INSERT INTO estados VALUES('Sonora')",
            "INSERT INTO estados VALUES('Tabasco')",
            "INSERT INTO estados VALUES('Tamaulipas')",
            "INSERT INTO estados VALUES('Tlaxcala')",
            "INSERT INTO estados VALUES('Veracruz')",
            "INSERT INTO estados VALUES('Yucatán')",
            "INSERT INTO estados VALUES('Zacatecas')"
        };

        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("create", "true");
            props.put("user", USER);
            props.put("password", PASSWORD);
            props.put("territory", "es_MX");//If you do not specify the territory=ll_CC attribute when you create the database, 
            //                                  Derby uses the java.util.Locale.getDefault method
            props.put("collation", "TERRITORY_BASED:PRIMARY");
            //COLLATION: para queries e inserts :PRIMARY ignoran mayusculas y acentos
            conn = DriverManager.getConnection(PROTOCOL + DB_NAME, props);
            configurarAutorizacion(conn);
            // Empezar transaccion
            conn.setAutoCommit(false);
            Statement s = conn.createStatement();
            s.executeUpdate(CREAR_TABLA_ASEGURADOS);
            s.executeUpdate(CREAR_TABLA_TELEFONOS);
            s.executeUpdate(CREAR_TABLA_EMAILS);
            s.executeUpdate(CREAR_TABLA_ESTADOS);
            s.executeUpdate(CREAR_TABLA_DOMICILIOS);
            s.executeUpdate(CREAR_TABLA_ASEGURADORAS);
            s.executeUpdate(CREAR_TABLA_RAMOS);
            s.executeUpdate(CREAR_TABLA_POLIZAS);
            s.executeUpdate(CREAR_TABLA_BENEFICIARIOS);
            s.executeUpdate(CREAR_TABLA_RECIBOS);
            s.executeUpdate(CREAR_INDEX_RECIBOS_COBRANZA);
            s.executeUpdate(CREAR_INDEX_RECIBOS_CUBRE_DESDE);
            for (String insert : INSERTS_ASEGURADORAS) {
                s.executeUpdate(insert);
            }
            for (String insert : INSERTS_RAMOS) {
                s.executeUpdate(insert);
            }
            for (String insert : INSERTS_ESTADOS) {
                s.executeUpdate(insert);
            }
            //commit transaccion
            conn.commit();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    try {
                        conn.close();
                    } catch (SQLException closeEx) {
                        rollbackEx.setNextException(closeEx);
                        throw rollbackEx;
                    }
                }
            }
        }
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void endTransaction() throws SQLException {
        connection.setAutoCommit(true);
    }

    public void commit() throws SQLException {
        connection.commit();
//        connection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void detenerBaseDeDatos() throws SQLException {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException ex) {
            if (((ex.getErrorCode() == 50000)
                    && ("XJ015".equals(ex.getSQLState())))) {
                // we got the expected exception
//                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            } else {
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
//                System.err.println("Derby did not shut down normally");
                throw ex;
            }
        }
    }

    public static void borrarBaseDeDatos() throws IOException {

        try {
            Files.delete(Paths.get("derby.log"));
            Path start = Paths.get("cartera");

            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                    if (e == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        // directory iteration failed
                        throw e;
                    }
                }
            });

        } catch (IOException ex) {
            throw ex;
        }
    }

    public void popularBaseDeDatos(String user, String password, List<Asegurado> asegurados) {
        Connection conn = null;
        PreparedStatement psInsert = null;
        Statement s;
        ResultSet rs = null;
        try {
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);
            conn = DriverManager.getConnection(PROTOCOL + DB_NAME, props);
            for (Asegurado asegurado : asegurados) {
//                psInsert = conn.prepareStatement("INSERT INTO asegurados VALUES(DEFAULT, ")
            }

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }

            }
        }
    }

    public void insertPoliza(Poliza poliza) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement("INSERT INTO polizas VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, poliza.getNumero().toUpperCase());
            preparedStatement.setString(2, poliza.getAseguradora().toUpperCase());
            preparedStatement.setInt(3, poliza.getAseguradoId());
            preparedStatement.setString(4, poliza.getRamo().toString().replace("_", " ").toUpperCase());//GASTOS_MEDICOS -> GASTOS MEDICOS
            preparedStatement.setString(5, poliza.getProducto().toUpperCase());
            preparedStatement.setString(6, poliza.getPlan().toUpperCase());
            preparedStatement.setDate(7, java.sql.Date.valueOf(poliza.getInicioVigencia()));
            preparedStatement.setDate(8, java.sql.Date.valueOf(poliza.getFinVigencia()));
            preparedStatement.setString(9, poliza.getConductoDeCobro().toString().replace("_", " ").toUpperCase());
            preparedStatement.setString(10, poliza.getFormaDePago().toString().toUpperCase());
            preparedStatement.setBigDecimal(11, poliza.getPrima().getCantidad());
            preparedStatement.setString(12, poliza.getPrima().getMoneda().toString().toUpperCase());
            preparedStatement.setBigDecimal(13, poliza.getDeducible().map(Dinero::getCantidad).orElse(null));
//            preparedStatement.setBigDecimal(13, (poliza.getDeducible().isPresent() ? poliza.getDeducible().map(Dinero::getCantidad) : null));
//            preparedStatement.setString(14, (poliza.getDeducible().isPresent() ? poliza.getDeducible().map(Dinero::getMoneda).map(Enum::toString) : null));
            preparedStatement.setString(14, poliza.getDeducible().map(Dinero::getMoneda).map(Enum::toString).orElse(null));
            preparedStatement.setBigDecimal(15, poliza.getSumaAsegurada().map(Dinero::getCantidad).orElse(null));
            preparedStatement.setString(16, poliza.getSumaAsegurada().map(Dinero::getMoneda).map(Enum::toString).orElse(null));
//            preparedStatement.setShort(17, poliza.getCoaseguro().orElse(null));
            preparedStatement.setObject(17, poliza.getCoaseguro().orElse(null), JDBCType.SMALLINT);
            preparedStatement.setString(18, poliza.getComentarios().orElse(null));
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    preparedStatement = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }

    }

    public List<Poliza> selectTodoPolizas() {
        Connection conn = null;
        ResultSet resultSet = null;
        List<Poliza> polizas = new ArrayList<Poliza>();
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            resultSet = s.executeQuery("SELECT * FROM polizas");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String numero = resultSet.getString(2);
                String aseguradora = resultSet.getString(3);
                int aseguradoId = resultSet.getInt(4);
                String ramo = resultSet.getString(5);
                String producto = resultSet.getString(6);
                String plan = resultSet.getString(7);
                LocalDate inicioVigencia = resultSet.getDate(8).toLocalDate();
                LocalDate finVigencia = resultSet.getDate(9).toLocalDate();
                String conductoCobro = resultSet.getString(10);
                String formaPago = resultSet.getString(11);
                BigDecimal prima = resultSet.getBigDecimal(12);
                String moneda = resultSet.getString(13);
                //POSIBLEMENTE NULL
                BigDecimal deducible = resultSet.getBigDecimal(14);
                String monedaDeducible = resultSet.getString(15);
                BigDecimal sumaAsegurada = resultSet.getBigDecimal(16);
                String monedaSumaAsegurada = resultSet.getString(17);
                Short coaseguro = resultSet.getShort(18);
                String comentarios = resultSet.getString(19);
                //
                Poliza poliza = new Poliza(numero, aseguradora, aseguradoId, ramo, producto, plan, inicioVigencia, conductoCobro, formaPago, new Dinero(prima, moneda));
                poliza.setId(id);
                if (sumaAsegurada != null) {
                    poliza.setSumaAsegurada(new Dinero(sumaAsegurada, monedaSumaAsegurada));
                }
                if (coaseguro != null) {
                    poliza.setCoaseguro(coaseguro);
                }
                if (deducible != null) {
                    poliza.setDeducible(new Dinero(deducible, monedaDeducible));
                }
                if (comentarios != null) {
                    poliza.setComentarios(comentarios);
                }
                polizas.add(poliza);
            }

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                    resultSet = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
        return polizas;
    }

    public void insertRecibo(Recibo recibo) throws SQLException {
        Connection conn = null;
        PreparedStatement prepStatement = null;
        try {
            conn = getConnection();
            prepStatement = conn.prepareStatement("INSERT INTO recibos VALUES(?,?,?,?,?)");
            prepStatement.setInt(1, recibo.getPolizaId());
            prepStatement.setDate(2, java.sql.Date.valueOf(recibo.getCubreDesde()));
            prepStatement.setDate(3, java.sql.Date.valueOf(recibo.getCubreHasta()));
            prepStatement.setBigDecimal(4, recibo.getImporte().getCantidad());
            prepStatement.setString(5, recibo.getCobranza().toString());
            prepStatement.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
//            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (prepStatement != null) {
                try {
                    prepStatement.close();
                    prepStatement = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
    }

    public List<Recibo> selectRecibos(Poliza poliza) throws SQLException {
        List<Recibo> recibos = new ArrayList<Recibo>();
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement prepStatement = null;
        try {
            conn = getConnection();
            prepStatement = conn.prepareStatement("SELECT * FROM recibos WHERE polizaId=?");
            prepStatement.setInt(1, poliza.getId());
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                LocalDate cubreDesde = resultSet.getDate(2).toLocalDate();
                LocalDate cubreHasta = resultSet.getDate(3).toLocalDate();
                BigDecimal importe = resultSet.getBigDecimal(4);
                String cobranza = resultSet.getString(5);
                Recibo recibo = new Recibo(poliza.getId(), cubreDesde, cubreHasta, new Dinero(importe, poliza.getPrima().getMoneda()), cobranza);
                recibos.add(recibo);
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return recibos;

    }

    public void insertAsegurado(Asegurado asegurado) throws SQLException {
        Connection conn = null;
        PreparedStatement prepStatement = null;
        try {
            conn = getConnection();
            prepStatement = conn.prepareStatement("INSERT INTO asegurados VALUES(DEFAULT,?,?,?,?,?)");
            prepStatement.setString(1, asegurado.getNombre().toUpperCase());
            prepStatement.setString(2, asegurado.getApellidoPaterno().map(String::toUpperCase).orElse(""));
            prepStatement.setString(3, asegurado.getApellidoMaterno().map(String::toUpperCase).orElse(""));
            prepStatement.setString(4, asegurado.getRFC().map(String::toUpperCase).orElse(null));
            prepStatement.setDate(5, (asegurado.getNacimiento().isPresent() ? java.sql.Date.valueOf(asegurado.getNacimiento().get()) : null));
            prepStatement.executeUpdate();
//            detenerBaseDeDatos();
            //TODO: INSERT polizas y recibos

        } catch (SQLException ex) {
            throw ex;
//            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (prepStatement != null) {
                try {
                    prepStatement.close();
                    prepStatement = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
    }

    public void contectarABaseDeDatos() throws SQLException {
        crearConnection();
    }

    private void crearConnection() throws SQLException {
        if (connection == null) {
            Properties props = new Properties();
            props.put("user", USER);
            props.put("password", PASSWORD);
            connection = DriverManager.getConnection(PROTOCOL + DB_NAME, props);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Asegurado> selectTodosAsegurados() {
        Connection conn = null;
        ResultSet resultSet = null;
        List<Asegurado> asegurados = new ArrayList<Asegurado>();
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            resultSet = s.executeQuery("SELECT * FROM asegurados");
            while (resultSet.next()) {
                Asegurado asegurado = new Asegurado(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                asegurado.setRFC(resultSet.getString(5));
                asegurado.setNacimiento(resultSet.getDate(6));
                asegurado.setId(resultSet.getInt(1));
                asegurados.add(asegurado);

            }

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                    resultSet = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
        return asegurados;
    }

    public List<Asegurado> buscarPorParemetros(ParametrosAsegurado params) {
        AseguradoMapper mapper = new AseguradoMapper();
        String query = mapper.createQuery(params);
        Connection conn = null;
        ResultSet resultSet = null;
        List<Asegurado> asegurados = new ArrayList<Asegurado>();
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                Asegurado asegurado = new Asegurado(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                asegurado.setRFC(resultSet.getString(5));
                asegurado.setNacimiento(resultSet.getDate(6));
                asegurado.setId(resultSet.getInt(1));
                asegurados.add(asegurado);

            }

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                    resultSet = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
        return asegurados;
    }

    public Optional<Asegurado> selectAsegurado(int id) {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement prepStatement = null;
        Optional<Asegurado> maybeAsegurado = Optional.empty();
        try {
            conn = getConnection();
            prepStatement = conn.prepareStatement("SELECT * FROM asegurados WHERE aseguradoId=?");
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                Asegurado asegurado = new Asegurado(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                asegurado.setRFC(resultSet.getString(5));
                asegurado.setNacimiento(resultSet.getDate(6));
                asegurado.setId(resultSet.getInt(1));
                maybeAsegurado = Optional.of(asegurado);
            }// TODO: si no lo encuentra pasar null o exception ?

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                    resultSet = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            if (prepStatement != null) {
                try {
                    prepStatement.close();
                    prepStatement = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
        return maybeAsegurado;
    }

    private void configurarAutorizacion(Connection conn) throws SQLException {

        String setProperty = "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(";
//        String getProperty = "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY(";
        String requireAuth = "'derby.connection.requireAuthentication'";
        String sqlAuthorization = "'derby.database.sqlAuthorization'";
//        String defaultConnMode = "'derby.database.defaultConnectionMode'";
        String fullAccessUsers = "'derby.database.fullAccessUsers'";
        String sequencePreallocator = "'derby.language.sequence.preallocator'";
//        String readOnlyAccessUsers = "'derby.database.readOnlyAccessUsers'";
        String provider = "'derby.authentication.provider'";
        String propertiesOnly = "'derby.database.propertiesOnly'";

//        System.out.println("Turning on authentication and SQL authorization.");
        Statement s = conn.createStatement();

        // Set requireAuthentication
        s.executeUpdate(setProperty + requireAuth + ", 'true')");
        // Set sqlAuthorization
        s.executeUpdate(setProperty + sqlAuthorization + ", 'true')");

        s.executeUpdate(setProperty + sequencePreallocator + ", '1')");
        // Retrieve and display property values
//        ResultSet rs = s.executeQuery(getProperty + requireAuth + ")");
//        rs.next();
//        System.out.println("Value of requireAuthentication is " + rs.getString(1));
//
//        rs = s.executeQuery(getProperty + sqlAuthorization + ")");
//        rs.next();
//        System.out.println("Value of sqlAuthorization is " + rs.getString(1));
        // Set authentication scheme to Derby builtin
        s.executeUpdate(setProperty + provider + ", 'BUILTIN')");

        // Create some sample users
        s.executeUpdate(setProperty + "'derby.user." + USER + "', '" + PASSWORD + "')");
//        s.executeUpdate(setProperty + "'derby.user.guest', 'java5w6x')");
//        s.executeUpdate(setProperty + "'derby.user.mary', 'little7xylamb')");
//        s.executeUpdate(setProperty + "'derby.user.sqlsam', 'light8q9bulb')");

        // Define noAccess as default connection mode
//        s.executeUpdate(setProperty + defaultConnMode + ", 'noAccess')");
        // Confirm default connection mode
//        rs = s.executeQuery(getProperty + defaultConnMode + ")");
//        rs.next();
//        System.out.println("Value of defaultConnectionMode is " + rs.getString(1));
        // Define read-write users
        s.executeUpdate(setProperty + fullAccessUsers + ", '" + USER + "')");

        // Define read-only user
//        s.executeUpdate(setProperty + readOnlyAccessUsers + ", 'guest')");
        // Therefore, user sa has no access
        // Confirm full-access users
//        rs = s.executeQuery(getProperty + fullAccessUsers + ")");
//        rs.next();
//        System.out.println("Value of fullAccessUsers is " + rs.getString(1));
        // Confirm read-only users
//        rs = s.executeQuery(getProperty + readOnlyAccessUsers + ")");
//        rs.next();
//        System.out.println("Value of readOnlyAccessUsers is " + rs.getString(1));
        // We would set the following property to TRUE only when we were
        // ready to deploy. Setting it to FALSE means that we can always
        // override using system properties if we accidentally paint
        // ourselves into a corner.
        s.executeUpdate(setProperty + propertiesOnly + ", 'false')");
        s.close();
    }

    public void borrarPolizas() {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            s.executeUpdate("DELETE FROM polizas");
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
    }

    public void borrarAsegurados() {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            s.executeUpdate("DELETE FROM asegurados");
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
    }

    public static void printSQLException(SQLException e) {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null) {
            System.out.println("\n----- SQLException -----");
            System.out.println("  SQL State:  " + e.getSQLState());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

}
