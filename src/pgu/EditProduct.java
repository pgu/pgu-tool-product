package pgu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditProduct extends HttpServlet {

    private final Helper h  = new Helper();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String reference = req.getParameter("reference");

        if (h.isVoid(reference)) {
            dispatchToNewProduct(req, resp);

        } else {

            if (!DB.ref2product.containsKey(reference)) {
                dispatchToNewProduct(req, resp);

            } else {
                final Product product = DB.ref2product.get(reference);
                req.setAttribute("product", product);

                final RequestDispatcher dispatch = req.getRequestDispatcher("WEB-INF/jsp/updateProduct.jsp");
                dispatch.forward(req, resp);
            }
        }
    }

    private void dispatchToNewProduct(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        final RequestDispatcher dispatch = req.getRequestDispatcher("WEB-INF/jsp/newProduct.jsp");
        dispatch.forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(" do post: " + req.getParameter("reference") + ", " + req.getParameter("designation"));
    }

}
