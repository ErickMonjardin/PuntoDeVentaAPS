package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Named("temaBean")
@SessionScoped
public class TemaBean implements Serializable {
    private String temaActual = "saga";

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("temaInventario".equals(cookie.getName())) {
                    temaActual = cookie.getValue();
                    break;
                }
            }
        }
    }

    public String getTemaActual(){
        return temaActual;
    }

    public void setTemaActual(String temaActual) {
        this.temaActual = temaActual;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        Cookie cookie = new Cookie("temaInventario", temaActual);
        cookie.setMaxAge(60 * 60 * 24 * 30); // La cookie vivirá por 30 días
        cookie.setPath("/"); // Disponible para toda la aplicación
        response.addCookie(cookie);
    }
}
