package pgu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class ProductServlet extends HttpServlet {

    private final Helper h = new Helper();

    private String pictures_folder ="";

    @Override
    public void init(final ServletConfig config) throws ServletException {

        pictures_folder = config.getServletContext().getInitParameter("pictures_folder");

        super.init(config);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
    IOException {

        final String requestURI = req.getRequestURI();

        req.setAttribute(Config.RECIPIENT, Config.recipient);

        //
        // for pictures
        // >> rq uri: /productsTool/products/BBB/pictures
        // >> rq path: /BBB/pictures
        //
        // for new
        // >> rq uri: /productsTool/products/new
        // >> rq path: null

        try {
            if (requestURI.endsWith("/new")) {
                createProduct(req, resp);

            } else if (requestURI.endsWith("/pictures")) {
                findPictures(req, resp);

            } else if (requestURI.contains("/pictures/")) {
                findPicture(req, resp);

            } else {
                findProduct(req, resp);
            }

        } catch (final IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    private void findPicture(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

        final String requestURI = req.getRequestURI();
        final int idxEnd = requestURI.lastIndexOf("/");
        final String filename = requestURI.substring(idxEnd + "/".length());

        final String reference = parseReferenceFromPicturesUrl(req);
        checkReferenceExists(resp, reference);
        final Product product = DB.ref2product.get(reference);

        File file = null;
        try {
            final int i = Integer.valueOf(filename);

            if (i < 0 || i >= product.pictures.size()) {
                throwException(404, String.format("Unknown picture for the reference [%s] !", reference), resp);
            }

            final Iterator<String> iterator = product.pictures.iterator();
            for (int j = 0; j < product.pictures.size(); j++) {
                final String picture = iterator.next();
                if (i == j) {
                    file = new File(pictures_folder, picture);
                    break;
                }
            }

        } catch (final NumberFormatException e) {

            if (!product.pictures.contains(filename)) {
                throwException(404, String.format("Unknown picture for the reference [%s] !", reference), resp);
            }

            file = new File(pictures_folder, filename);
        }


        resp.setHeader("Content-Type", getServletContext().getMimeType(filename));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        //        resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");

        final ServletOutputStream output = resp.getOutputStream();
        final FileInputStream input = new FileInputStream(file);

        try {
            IOUtils.copy(input, output);

        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
        }

    }

    private void findPictures(final HttpServletRequest req, final HttpServletResponse resp) throws IOException,
    ServletException {
        // /products/99/pictures

        final String reference = parseReferenceFromPicturesUrl(req);

        checkReferenceExists(resp, reference);

        final Product product = DB.ref2product.get(reference);

        req.setAttribute("product", product);

        final ArrayList<String> references = new ArrayList<String>(DB.ref2product.keySet());
        Collections.sort(references);
        req.setAttribute("references", references);

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/pictures.jsp");
        dispatch.forward(req, resp);
    }

    private String parseReferenceFromPicturesUrl(final HttpServletRequest req) {

        final String requestURI = req.getRequestURI();
        final int idxEnd = requestURI.indexOf("/pictures");
        final String product_path = requestURI.substring(0, idxEnd);

        final int lastSlash = product_path.lastIndexOf("/");
        final String reference = product_path.substring(lastSlash + "/".length());
        return reference;
    }

    private void createProduct(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
    IOException {

        final ArrayList<String> references = new ArrayList<String>(DB.ref2product.keySet());
        Collections.sort(references);
        req.setAttribute("references", references);

        final RequestDispatcher dispatch = req.getRequestDispatcher("/WEB-INF/jsp/product_edition.jsp");
        dispatch.forward(req, resp);
    }

    private void throwException(final int code, final String err_msg, final HttpServletResponse resp)
            throws IOException {

        resp.sendError(code, err_msg);
        throw new IllegalArgumentException(err_msg);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
    IOException {

        final String requestURI = req.getRequestURI();
        req.setAttribute(Config.RECIPIENT, Config.recipient);

        try {

            if (requestURI.endsWith("/pictures")) {
                createPicture(req, resp);

            } else {

                final String method = req.getParameter("method");

                if (h.isBlank(method)) {
                    putProduct(req, resp);

                } else if ("delete".equalsIgnoreCase(method)) {
                    deleteProduct(req, resp);

                } else {
                    throwException(400, String.format("Unknown method [%s] !", method), resp);
                }
            }
        } catch (final IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    private void createPicture(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        final String reference = parseReferenceFromPicturesUrl(req);

        checkReferenceExists(resp, reference);

        final Product product = DB.ref2product.get(reference);

        try {
            final List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (final FileItem item : items) {

                if ("file".equals(item.getFieldName())) {

                    final String filename = FilenameUtils.getName(item.getName());
                    final InputStream content = item.getInputStream();

                    final File file = new File(pictures_folder, filename);
                    final OutputStream output = new FileOutputStream(file);

                    try {
                        IOUtils.copy(content, output);
                    } finally {
                        IOUtils.closeQuietly(output);
                        IOUtils.closeQuietly(content);
                    }

                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write("File " + filename + " successfully uploaded");

                    product.pictures.add(filename);
                    return;
                }
            }
        } catch (final FileUploadException e) {
            throw new ServletException("Parsing file upload failed.", e);
        }

    }

    private void deleteProduct(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String reference = parseReference(req);

        checkReferenceExists(resp, reference);

        DB.ref2product.remove(reference);

        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void checkReferenceExists(final HttpServletResponse resp, final String reference) throws IOException {

        if (h.isBlank(reference)) {
            throwException(400, "Product reference is empty!", resp);
        }

        if (!DB.ref2product.containsKey(reference)) {
            throwException(404, String.format("Unknown product for the reference [%s] !", reference), resp);
        }
    }

    private void findProduct(final HttpServletRequest req, final HttpServletResponse resp) throws IOException,
    ServletException {

        final String reference = parseReference(req);

        checkReferenceExists(resp, reference);

        final Product product = DB.ref2product.get(reference);
        req.setAttribute("product", product);

        final ArrayList<String> references = new ArrayList<String>(DB.ref2product.keySet());
        Collections.sort(references);
        req.setAttribute("references", references);

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

        /////////////////
        String paramFrameId = "";

        final String frameId = req.getParameter("frameId");
        if (!h.isBlank(frameId) && !"null".equals(frameId)) {
            paramFrameId = "?frameId=" + frameId;
        }

        resp.sendRedirect(req.getContextPath() + "/products/" + product.reference + paramFrameId);
    }

    private String parseReference(final HttpServletRequest req) {

        final String pathInfo = req.getPathInfo();
        final int lastSlash = pathInfo.lastIndexOf("/");

        return pathInfo.substring(lastSlash + "/".length());
    }

}
