package iuh.fit.servlet;

import iuh.fit.dao.ProductDAO;
import iuh.fit.model.CartBean;
import iuh.fit.model.Product;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Resource(name="jdbc/shopdb")
    private DataSource dataSource;
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cart = (CartBean) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartBean();
            session.setAttribute("cart", cart);
        }

        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Product p = productDAO.getProductById(Integer.parseInt(req.getParameter("id")));
            cart.addProduct(p);
        } else if ("update".equals(action)) {
            cart.updateQuantity(Integer.parseInt(req.getParameter("productId")),
                    Integer.parseInt(req.getParameter("quantity")));
        } else if ("remove".equals(action)) {
            cart.removeProduct(Integer.parseInt(req.getParameter("productId")));
        } else if ("clear".equals(action)) {
            cart.clear();
        }
        resp.sendRedirect("cart");
    }
}
