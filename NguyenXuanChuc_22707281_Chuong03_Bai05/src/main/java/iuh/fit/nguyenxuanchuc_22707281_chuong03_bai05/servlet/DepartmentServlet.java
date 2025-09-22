package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.servlet;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.dao.DepartmentDAO;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model.Department;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {
    private final DepartmentDAO dao = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("form".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Department d = dao.findById(Integer.parseInt(id));
                req.setAttribute("department", d);
            }
            req.getRequestDispatcher("/department-form.jsp").forward(req, resp);
            return;
        }

        String keyword = req.getParameter("keyword");
        List<Department> list = (keyword == null || keyword.isEmpty())
                ? dao.getAll()
                : dao.search(keyword);
        req.setAttribute("departments", list);
        req.getRequestDispatcher("/department-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            dao.insert(new Department(0, req.getParameter("name"), req.getParameter("note")));
        } else if ("update".equals(action)) {
            dao.update(new Department(
                    Integer.parseInt(req.getParameter("id")),
                    req.getParameter("name"),
                    req.getParameter("note")));
        } else if ("delete".equals(action)) {
            dao.delete(Integer.parseInt(req.getParameter("id")));
        }
        resp.sendRedirect("departments");
    }
}
