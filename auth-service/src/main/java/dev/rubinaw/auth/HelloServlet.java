package dev.rubinaw.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Provides a simple health-style endpoint for the auth service.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    /**
     * Returns a plain text response that shows the service is running.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws ServletException when servlet processing fails
     * @throws IOException when request or response handling fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        resp.getWriter().write("Auth Service läuft");
    }
}
