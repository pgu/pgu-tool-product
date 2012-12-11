package pgu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConfigServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute(Config.RECIPIENT, Config.recipient);

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/config.jsp");
        dispatch.forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String recipient = req.getParameter(Config.RECIPIENT);
        Config.recipient = recipient;

        req.setAttribute(Config.RECIPIENT, Config.recipient);
        req.setAttribute("msg_success", "Config is updated!");

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/config.jsp");
        dispatch.forward(req, resp);
    }



}
