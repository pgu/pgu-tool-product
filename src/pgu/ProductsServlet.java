package pgu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ProductsServlet extends HttpServlet {

    private final Helper h  = new Helper();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final Collection<Product> db_products = DB.ref2product.values();

        final ArrayList<Product> products = new ArrayList<Product>(db_products);
        Collections.sort(products, compByRef());

        req.setAttribute("products", products);

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/products.jsp");
        dispatch.forward(req, resp);
    }

    private Comparator<Product> compByRef() {
        return new Comparator<Product>() {

            @Override
            public int compare(final Product o1, final Product o2) {
                return o1.reference.compareTo(o2.reference);
            }
        };
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        final String reference = req.getParameter("reference");

        if (h.isVoid(reference)) {

            resp.sendError(400, "The reference is empty!");
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

        resp.sendRedirect("products/" + product.reference);
    }

}
