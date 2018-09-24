/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author emilio
 */
public class Configuracion {

    private Properties propiedades;
    private final String CONFIG_PATH = "config/cartera.config";
    private final String BD_DEFAULT_USUARIO_KEY = "bd.default.usuario";
    private final String BD_DEFAULT_USUARIO_VALUE = "admin";
    private final String BD_DEFAULT_PASSWORD_KEY = "bd.default.password";
    private final String BD_DEFAULT_PASSWORD_VALUE = "admin";
    private final String BD_USUARIO_KEY = "bd.usuario";
    private final String BD_PASSWORD_KEY = "bd.password";
    private final String USUARIO_CORREO_KEY = "usuario.correo";
    private final String USUARIO_CORREO_PASSWORD_KEY = "usuario.correo.password";

    private static Configuracion instance;

    private Configuracion() {
        propiedades = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(CONFIG_PATH);
            propiedades.load(in);
        } catch (FileNotFoundException ex) {
            createConfigFileAndLoadConfigurations();
        } catch (IOException ex) {
//            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException ex) {
//            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static Configuracion getInstance() {
        if (instance == null) {
            instance = new Configuracion();
        }
        return instance;
    }

    public Properties getProperties() {
        return propiedades;
    }

    public void guardarUsuarioBaseDeDatos(String usuario) throws FileNotFoundException, IOException {
        propiedades.put(BD_USUARIO_KEY, usuario);
        guardarConfiguracion("");
    }

    public void guardarPasswordBaseDeDatos(String password) throws FileNotFoundException, IOException {
        propiedades.put(BD_PASSWORD_KEY, password);
        guardarConfiguracion("");
    }

    public void guardarCorreoUsuario(String correo) throws FileNotFoundException, IOException {
        propiedades.put(USUARIO_CORREO_KEY, correo);
        guardarConfiguracion("");
    }

    public void guardarPasswordCorreoUsuario(String password) {
        propiedades.put(USUARIO_CORREO_PASSWORD_KEY, password);
        guardarConfiguracion("");
    }

    public String getUsuarioBaseDeDatos() {
        return propiedades.getProperty(BD_USUARIO_KEY);
    }

    public String getPasswordBaseDeDatos() {
        return propiedades.getProperty(BD_PASSWORD_KEY);
    }

    public String getCorreoUsuario() {
        return propiedades.getProperty(USUARIO_CORREO_KEY);
    }

    public String getPasswordCorreoUsuario() {
        return propiedades.getProperty(USUARIO_CORREO_PASSWORD_KEY);
    }
    
    public String getDefaultBDUser() {
        return propiedades.getProperty(BD_DEFAULT_USUARIO_KEY);
    }
    
    public String getDefaultBDPassword() {
        return propiedades.getProperty(BD_DEFAULT_PASSWORD_KEY);
    }

    private void createConfigFileAndLoadConfigurations() {
        FileInputStream in = null;
        try {
            File config = new File(CONFIG_PATH);
            config.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(config);
            writer.write(BD_DEFAULT_USUARIO_KEY + "=" + BD_DEFAULT_USUARIO_VALUE + "\n");
            writer.write(BD_DEFAULT_PASSWORD_KEY + "=" + BD_DEFAULT_PASSWORD_VALUE + "\n");
            writer.flush();
            writer.close();
            in = new FileInputStream(CONFIG_PATH);
            propiedades.load(in);
        } catch (IOException ex) {

        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException ex) {
//            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void guardarConfiguracion(String comentario) {
        try {
            FileOutputStream out = new FileOutputStream(CONFIG_PATH);
            propiedades.store(out, comentario);
        } catch (FileNotFoundException ex) {
            createConfigFileAndLoadConfigurations();
        } catch (IOException ex) {
//            throw ex;
        }
    }

}
