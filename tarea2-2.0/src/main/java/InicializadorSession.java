
import java.time.LocalDate;
import java.util.Set;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import logica.controllers.IControllerEvento;
import logica.models.Factory;

@WebListener
public class InicializadorSession implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        IControllerEvento iEvento = Factory.getInstance().getControllerEvento();
        Set<String> categorias = iEvento.listarCategorias();

        event.getSession().setAttribute("usuario", null);
        event.getSession().setAttribute("categorias", categorias);
        event.getSession().setAttribute("fecha", LocalDate.now());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}
