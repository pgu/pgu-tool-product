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

        final String method = req.getParameter("method");

        try {

            if (h.isVoid(method)) { // GET
                findProduct(req, resp);

            } else if ("delete".equalsIgnoreCase(method)) {
                deleteProduct(req, resp);

            } else {

                throwException(400, String.format("Unknown method [%s] !", method), resp);
            }

        } catch (final IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    private void throwException(final int code, final String err_msg, final HttpServletResponse resp) throws IOException {

        resp.sendError(code, err_msg);
        throw new IllegalArgumentException(err_msg);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            putProduct(req, resp);

        } catch (final IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    private void deleteProduct(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String reference = parseReference(req);

        checkReferenceExists(resp, reference);

        DB.ref2product.remove(reference);

        resp.sendRedirect("products");
    }

    private void checkReferenceExists(final HttpServletResponse resp, final String reference) throws IOException {

        if (h.isVoid(reference)) {
            throwException(400, "Product reference is empty!", resp);
        }

        if (!DB.ref2product.containsKey(reference)) {
            throwException(404, String.format("Unknown product for the reference [%s] !", reference), resp);
        }
    }

    private void findProduct(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {

        final String reference = parseReference(req);

        checkReferenceExists(resp, reference);

        final Product product = DB.ref2product.get(reference);
        req.setAttribute("product", product);

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/product_edition.jsp");
        dispatch.forward(req, resp);
    }

    private void putProduct(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String reference = parseReference(req);

        checkReferenceExists(resp, reference);

        final Product product = DB.ref2product.get(reference);
        product.depth = req.getParameter("depth");
        product.designation = req.getParameter("designation");
        product.height = req.getParameter("height");
        product.weight = req.getParameter("weight");
        product.width = req.getParameter("width");

        resp.sendRedirect("products/" + product.reference);
    }

    private String parseReference(final HttpServletRequest req) {

        final String pathInfo = req.getPathInfo();
        final int lastSlash = pathInfo.lastIndexOf("/");

        return pathInfo.substring(lastSlash + "/".length());
    }

}
