package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TinTuc {
    private int matt;
    private String tieude;
    private String noidungtt;
    private String lienket;
    private int madm;
}
