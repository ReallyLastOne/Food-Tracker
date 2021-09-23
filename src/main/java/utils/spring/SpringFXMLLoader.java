package utils.spring;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class SpringFXMLLoader {

    public static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    public Object load(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        loader.setLocation(getClass().getResource(url));

        //loader.setResources(ResourceBundle.getBundle(resources));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Parent loadFXML(String fxmlLocation)
            throws IOException {

        //FXMLLoader loader = new FXMLLoader(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
        // load controllers from application context:
        loader.setControllerFactory(applicationContext::getBean);

        // load controls from application context, where available:
        loader.setBuilderFactory(new BuilderFactory() {
            JavaFXBuilderFactory defaultFactory = new JavaFXBuilderFactory();

            @Override
            public javafx.util.Builder<?> getBuilder(Class<?> type) {
                String[] beanNames = applicationContext.getBeanNamesForType(type);
                if (beanNames.length == 1) {
                    return (Builder<Object>) () -> applicationContext.getBean(beanNames[0]);
                } else {
                    return defaultFactory.getBuilder(type) ;
                }
            }
        });

        return loader.load();
    }
}