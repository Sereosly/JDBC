package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import java.io.IOException;
import java.io.InputStream;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final SessionFactory sessionFactory;


    static {
        try{
            Properties props = new Properties();
            InputStream input = Util.class.getClassLoader().getResourceAsStream("application.properties");
            if(input == null){
                throw new RuntimeException("Failed CONFIG");
            }
            props.load(input);

            Configuration configuration = new Configuration();
            configuration.addProperties(props);
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException | RuntimeException e){
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
