package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DanhMuc {
    private int madm;
    private String tenDanhMuc;
    private String nguoiQuanLy;
    private String ghiChu;
}
