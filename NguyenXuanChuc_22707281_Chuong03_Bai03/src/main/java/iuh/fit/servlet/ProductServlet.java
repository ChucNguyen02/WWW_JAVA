package iuh.fit.servlet;

import iuh.fit.dao.ProductDAO;
import iuh.fit.model.Product;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet({"/products", "/product"})
public class ProductServlet extends HttpServlet {
    @Resource(name="jdbc/shopdb")
    private DataSource dataSource;
    private ProductDAO productDAO;

    @Override
    public void init(){
        productDAO = new ProductDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            Product product = productDAO.getProductById(Integer.parseInt(id));
            req.setAttribute("product", product);
            req.getRequestDispatcher("product-detail.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("products", productDAO.getAllProducts());
        req.getRequestDispatcher("product-list.jsp").forward(req, resp);
    }
}
