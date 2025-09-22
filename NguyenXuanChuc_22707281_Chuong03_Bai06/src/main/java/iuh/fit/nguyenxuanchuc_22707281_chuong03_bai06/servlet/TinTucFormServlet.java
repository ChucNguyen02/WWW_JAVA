package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.servlet;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao.DanhMucDAO;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao.DanhSachTinTucQuanLy;
import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model.TinTuc;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@WebServlet("/tintucform")
public class TinTucFormServlet extends HttpServlet {
    private final DanhMucDAO dmDao = new DanhMucDAO();
    private final DanhSachTinTucQuanLy ttDao = new DanhSachTinTucQuanLy();

    // regex: link must start with "http://"
    private final Pattern linkPattern = Pattern.compile("^http://.*", Pattern.CASE_INSENSITIVE);
    // content length <=255 enforced by DB and client-side; here check length
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("danhmucs", dmDao.getAll());
        req.getRequestDispatcher("/tintucform.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String tieude = req.getParameter("tieude");
        String noidung = req.getParameter("noidungtt");
        String lienket = req.getParameter("lienket");
        String madmStr = req.getParameter("madm");

        Map<String,String> errors = new HashMap<>();

        // Required checks
        if (tieude == null || tieude.trim().isEmpty()) errors.put("tieude", "Tiêu đề bắt buộc");
        if (noidung == null || noidung.trim().isEmpty()) errors.put("noidungtt", "Nội dung bắt buộc");
        else if (noidung.length() > 255) errors.put("noidungtt", "Nội dung không quá 255 ký tự");
        if (lienket == null || lienket.trim().isEmpty()) errors.put("lienket", "Liên kết bắt buộc");
        else if (!linkPattern.matcher(lienket.trim()).matches()) errors.put("lienket", "Liên kết phải bắt đầu bằng http://");
        int madm = 0;
        try {
            madm = Integer.parseInt(madmStr);
        } catch (Exception ex) {
            errors.put("madm", "Danh mục phải được chọn");
        }

        if (!errors.isEmpty()) {
            // trả về form kèm lỗi và dữ liệu đã nhập
            req.setAttribute("errors", errors);
            req.setAttribute("tieude", tieude);
            req.setAttribute("noidungtt", noidung);
            req.setAttribute("lienket", lienket);
            req.setAttribute("selectedMadm", madm);
            req.setAttribute("danhmucs", dmDao.getAll());
            req.getRequestDispatcher("/tintucform.jsp").forward(req, resp);
            return;
        }

        // hợp lệ -> thêm vào DB
        TinTuc t = new TinTuc(0, tieude.trim(), noidung.trim(), lienket.trim(), madm);
        ttDao.insert(t);
        // chuyển sang trang kết quả hoặc list
        resp.sendRedirect(req.getContextPath() + "/danhsachtintuc?madm=" + madm);
    }
}
