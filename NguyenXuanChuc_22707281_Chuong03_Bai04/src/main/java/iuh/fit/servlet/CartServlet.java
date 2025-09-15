package iuh.fit.servlet;

import iuh.fit.dao.BookDAO;
import iuh.fit.model.Book;
import iuh.fit.model.CartBean;
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
    @Resource(name="jdbc/bookstore")
    private DataSource dataSource;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("giohang.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cart = (CartBean) session.getAttribute("cart");
        if (cart == null) { cart = new CartBean(); session.setAttribute("cart", cart); }

        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Book b = bookDAO.getBookById(Integer.parseInt(req.getParameter("id")));
            cart.addBook(b);
        } else if ("update".equals(action)) {
            cart.updateQuantity(Integer.parseInt(req.getParameter("bookId")),
                    Integer.parseInt(req.getParameter("quantity")));
        } else if ("remove".equals(action)) {
            cart.removeBook(Integer.parseInt(req.getParameter("bookId")));
        } else if ("clear".equals(action)) {
            cart.clear();
        }
        resp.sendRedirect("cart");
    }
}
