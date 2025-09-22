package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.servlet;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao.DanhMucDAO;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao.DanhSachTinTucQuanLy;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/quanly")
public class QuanLyFormServlet extends HttpServlet {
    private final DanhSachTinTucQuanLy ttDao = new DanhSachTinTucQuanLy();
    private final DanhMucDAO dmDao = new DanhMucDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("danhmucs", dmDao.getAll());
        req.setAttribute("tintucs", ttDao.getAll());
        req.getRequestDispatcher("/quanlyform.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // xử lý xóa
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            String mattStr = req.getParameter("matt");
            if (mattStr != null) {
                try {
                    int matt = Integer.parseInt(mattStr);
                    ttDao.delete(matt);
                } catch (Exception ignored) {}
            }
        }
        resp.sendRedirect(req.getContextPath() + "/quanly");
    }
}
