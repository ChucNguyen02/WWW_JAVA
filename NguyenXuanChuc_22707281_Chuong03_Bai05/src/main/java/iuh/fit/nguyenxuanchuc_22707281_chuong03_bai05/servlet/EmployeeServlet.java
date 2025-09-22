package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.servlet;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.dao.DepartmentDAO;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.dao.EmployeeDAO;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model.Department;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model.Employee;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeDAO empDAO = new EmployeeDAO();
    private final DepartmentDAO deptDAO = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String deptIdStr = req.getParameter("deptId");

        if ("form".equals(action)) {
            req.setAttribute("departments", deptDAO.getAll());
            String id = req.getParameter("id");
            if (id != null) {
                req.setAttribute("employee", empDAO.findById(Integer.parseInt(id)));
            }
            req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
            return;
        }

        List<Employee> list;
        String selectedDeptName = null;
        if (deptIdStr != null && !deptIdStr.isEmpty()) {
            int deptId = Integer.parseInt(deptIdStr);
            list = empDAO.getByDepartment(deptId);
            Department selectedDept = deptDAO.findById(deptId);
            if (selectedDept != null) selectedDeptName = selectedDept.getName();
            req.setAttribute("selectedDeptId", deptId);
        } else {
            list = empDAO.getAll();
        }

        req.setAttribute("employees", list);
        req.setAttribute("departments", deptDAO.getAll());
        req.setAttribute("selectedDeptName", selectedDeptName);
        req.getRequestDispatcher("/employee-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action) || "update".equals(action)) {
            int id = "update".equals(action) ? Integer.parseInt(req.getParameter("id")) : 0;
            Employee e = new Employee(
                    id,
                    req.getParameter("name"),
                    req.getParameter("email"),
                    req.getParameter("phone"),
                    req.getParameter("position"),
                    new BigDecimal(req.getParameter("salary")),
                    Integer.parseInt(req.getParameter("departmentId"))
            );
            if ("add".equals(action)) empDAO.insert(e); else empDAO.update(e);
        } else if ("delete".equals(action)) {
            empDAO.delete(Integer.parseInt(req.getParameter("id")));
        }
        resp.sendRedirect("employees");
    }
}
