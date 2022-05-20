package edu.ftv.training.Model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "DM_CNDV_SUDUNG")
@EntityListeners(AuditingEntityListener.class)
public class SuDung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MA")
    private String ma;

    @Column(name = "TEN_NGAN")
    private String tenNgan;

    @Column(name = "DIA_CHI_CN")
    private String diaChi;

    @Column(name = "LIEN_HE_DIEN_TOAN")
    private String lienHeDienToan;

    @Column(name = "LIEN_HE_KE_TOAN")
    private String lienHeKeToan;

    @LastModifiedDate
    @Column(name = "NGAY_TAO")
    private Date updatedDate;

    @CreatedDate
    @Column(name = "NGAY_SUA")
    private Date createdDate;

    @Column(name = "NGUOI_TAO")
    private String nguoiTao;

    @Column(name = "NGUOI_SUA")
    private String nguoiSua;

    @Column(name = "TEN_DAY_DU")
    private String tenDayDu;

    @Column(name = "MA_SO_THUE")
    private String maSoThue;
}
