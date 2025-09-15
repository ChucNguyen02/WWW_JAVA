package iuh.fit.servlet;

import iuh.fit.dao.BookDAO;
import iuh.fit.model.Book;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/books", "/book"})
public class BookServlet extends HttpServlet {
    @Resource(name = "jdbc/bookstore")
    private DataSource dataSource;
    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/book".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Book b = bookDAO.getBookById(id);
            req.setAttribute("book", b);
            req.getRequestDispatcher("chitietsach.jsp").forward(req, resp);
        } else {
            String keyword = req.getParameter("keyword");
            String minStr = req.getParameter("min");
            String maxStr = req.getParameter("max");

            Double min = (minStr != null && !minStr.isEmpty()) ? Double.valueOf(minStr) : null;
            Double max = (maxStr != null && !maxStr.isEmpty()) ? Double.valueOf(maxStr) : null;

            List<Book> books = bookDAO.searchBooks(keyword, min, max);
            req.setAttribute("books", books);
            req.getRequestDispatcher("danhsach.jsp").forward(req, resp);
        }
    }
}
