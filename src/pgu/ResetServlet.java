package pgu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ResetServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        DB.resetData();

        final StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (final Product product : DB.ref2product.values()) {
            sb.append("<li>");
            sb.append(product.reference);
            sb.append("</li>");
        }
        sb.append("</ul>");

        req.setAttribute("summary", String.format("The database contains %s items: <br/> %s", DB.ref2product.size(), sb.toString()));

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/reset.jsp");
        dispatch.forward(req, resp);
    }

}
