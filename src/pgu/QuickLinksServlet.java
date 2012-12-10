package pgu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class QuickLinksServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
    IOException {

        resp.getWriter().print(getQuickLinks());
    }

    private String getQuickLinks() {
        final String menuJson = "" + //
                "[                         " + //
                " {                        " + //
                "          'code': '%s'    " + //
                "  ,      'title': '%s'    " + //
                "  , 'menu_codes': [       " + //
                "                   '%s'   " + //
                "                  ]       " + //
                " }                        " + //
                "]                         " + //
                ""; //

        final String json = menuJson //
                .replaceAll("'", "\"") //
                .replaceAll("\\s", "") //
                ;

        return String.format(json //
                , "create_product" //
                , "Create a new product" //
                //
                , MenuServlet.PRODUCT_NEW //
                );
    }

}
