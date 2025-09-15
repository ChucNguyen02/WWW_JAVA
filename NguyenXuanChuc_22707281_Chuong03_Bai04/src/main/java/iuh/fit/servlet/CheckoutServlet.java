package iuh.fit.servlet;

import iuh.fit.model.CartBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String email = req.getParameter("email");

        HttpSession session = req.getSession();
        CartBean cart = (CartBean) session.getAttribute("cart");


        if (cart != null) {
            cart.clear();
            session.setAttribute("cart", cart);
        }

        req.setAttribute("message", "Cảm ơn " + name + "! Đơn hàng đã được xác nhận.");
        req.getRequestDispatcher("thanhtoan.jsp").forward(req, resp);
    }
}
