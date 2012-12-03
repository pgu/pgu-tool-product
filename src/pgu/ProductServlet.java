package pgu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ProductServlet extends HttpServlet {

    private final Helper h  = new Helper();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String reference = req.getParameter("reference");

        if (h.isVoid(reference)) {
            dispatchToNewProduct(req, resp);
            return;
        }

        if (!DB.ref2product.containsKey(reference)) {
            dispatchToNewProduct(req, resp);
            return;
        }

        final Product product = DB.ref2product.get(reference);
        req.setAttribute("product", product);

        final RequestDispatcher dispatch = req.getRequestDispatcher("WEB-INF/jsp/updateProduct.jsp");
        dispatch.forward(req, resp);
    }

    private void dispatchToNewProduct(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        final RequestDispatcher dispatch = req.getRequestDispatcher("WEB-INF/jsp/newProduct.jsp");
        dispatch.forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String reference = req.getParameter("reference");

        if (h.isVoid(reference)) {
            req.setAttribute("ref_error", "The reference is empty");
            dispatchToNewProduct(req, resp);
            return;
        }

        if (!req.getHeader("Referer").endsWith("updateProduct") //
                && DB.ref2product.containsKey(reference)) {

            req.setAttribute("ref_error", "The reference already exists");
            dispatchToNewProduct(req, resp);
            return;
        }

        final Product product = new Product();
        product.depth = req.getParameter("depth");
        product.designation = req.getParameter("designation");
        product.height = req.getParameter("height");
        product.reference = req.getParameter("reference");
        product.weight = req.getParameter("weight");
        product.width = req.getParameter("width");

        DB.ref2product.put(product.reference, product);

        req.setAttribute("product", product);

        final RequestDispatcher dispatch = req.getRequestDispatcher("WEB-INF/jsp/updateProduct.jsp");
        dispatch.forward(req, resp);
    }

}
