package pgu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MenuServlet extends HttpServlet {

    public static final String PRODUCT_NEW = "product_new";

    private static final String PICTURES = "pictures:";
    private static final String DESCRIPTION = "description:";

    private final Helper h = new Helper();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
    IOException {

        final String pathBase = getPathBase(req);
        final String code = extractCode(req);

        if (h.isBlank(code)) {
            throw new IllegalArgumentException("The code is blank!");
        }

        resp.getWriter().print(getMenu(code, pathBase));
    }

    private String extractCode(final HttpServletRequest req) {
        final String requestURI = req.getRequestURI();
        final int lastIndexOf = requestURI.lastIndexOf("/");

        return requestURI.substring(lastIndexOf + "/".length());
    }

    private String getPathBase(final HttpServletRequest req) {
        final String basePath = getServerUrl(req) + req.getContextPath();

        if (basePath.endsWith("/")) {
            return basePath;

        } else {
            return basePath + "/";
        }
    }

    private String getServerUrl(final HttpServletRequest request) {

        final String scheme = request.getScheme();
        final int port = getServerPort(request, scheme);

        try {
            final URL serverURL = new URL(scheme, request.getServerName(), port, "");
            return serverURL.toString();

        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getServerPort(final HttpServletRequest request, final String scheme) {
        final int port = request.getServerPort();

        if ("http".equals(scheme) && port == 80) {
            return -1;

        } else if ("https".equals(scheme) && port == 443) {
            return -1;

        } else {
            return port;
        }
    }

    private String getMenu(final String menuCode, final String pathBase) {
        if (PRODUCT_NEW.equals(menuCode)) {
            return json(menuCode, //
                    "New product", pathBase + "products/new", //
                    new String[] {menuCode});

        } else if (menuCode.startsWith(DESCRIPTION)) {
            final String productId = menuCode.substring(DESCRIPTION.length());

            final String picturesCode = PICTURES + productId;

            return json(menuCode, //
                    "Description", pathBase + "products/" + productId, //
                    new String[] {menuCode, picturesCode});

        } else if (menuCode.startsWith(PICTURES)) {
            final String productId = menuCode.substring(PICTURES.length());

            final String descriptionCode = DESCRIPTION + productId;

            return json(menuCode, //
                    "Pictures", pathBase + "products/" + productId + "/pictures", //
                    new String[] {descriptionCode, menuCode});

        } else {
            throw new IllegalArgumentException("Unknown menuCode: " + menuCode);
        }
    }

    private String json(final String code, final String title, final String url, final String[] codes) {

        final String menuJson = "" + //
                " {                   " + //
                "       'code': '%s'  " + //
                "  ,   'title': '%s'  " + //
                "  ,     'url': '%s'  " + //
                "  , 'context':  %s   " + //
                " }                   " //
                ;

        final String json = menuJson //
                .replaceAll("'", "\"") //
                .replaceAll("\\s", "") //
                ;

        return String.format(json //
                , code //
                , title //
                , url //
                , jsonArray(codes) //
                );
    }

    private String jsonArray(final String[] codes) {

        final StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (final String code : codes) {
            sb.append("\"");
            sb.append(code);
            sb.append("\"");
            sb.append(",");
        }

        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() -1); // remove trailing ","
        }

        sb.append("]");
        return sb.toString();
    }

}
