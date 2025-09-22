package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.servlet;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao.DanhMucDAO;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao.DanhSachTinTucQuanLy;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model.DanhMuc;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model.TinTuc;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/danhsachtintuc")
public class DanhSachTinTucServlet extends HttpServlet {
    private final DanhMucDAO dmDao = new DanhMucDAO();
    private final DanhSachTinTucQuanLy ttDao = new DanhSachTinTucQuanLy();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String madmStr = req.getParameter("madm");
        List<DanhMuc> dms = dmDao.getAll();
        req.setAttribute("danhmucs", dms);

        if (madmStr != null && !madmStr.isEmpty()) {
            int madm = Integer.parseInt(madmStr);
            List<TinTuc> tts = ttDao.getByDanhMuc(madm);
            req.setAttribute("tintucs", tts);
            DanhMuc selected = dmDao.findById(madm);
            req.setAttribute("selectedDanhMuc", selected);
        } else {
            // show all news or none — theo yêu cầu, ta show tất cả
            req.setAttribute("tintucs", ttDao.getAll());
        }
        req.getRequestDispatcher("/danhsachtintuc.jsp").forward(req, resp);
    }
}
