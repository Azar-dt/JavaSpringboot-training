package edu.ftv.training.Service;

import edu.ftv.training.Model.SuDung;
import edu.ftv.training.Model.SuDung_;
import edu.ftv.training.Repository.SuDungRepository;
import edu.ftv.training.payload.PagingRequest;
import edu.ftv.training.payload.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SuDungService {
    @Autowired
    private SuDungRepository suDungRepository;

    @Autowired
    private EntityManager entityManager;

    public List<SuDung> findAllSuDung() {
        return suDungRepository.findAll();
    }

    public PagingResponse searchByNativeQuery(PagingRequest pagingRequest) {
        SuDung suDung = pagingRequest.getSuDung();
        int limit = pagingRequest.getLimit();
        int currentPage = pagingRequest.getCurrentPage();
//        limit*currentPage
        List<SuDung> records = entityManager.createNativeQuery("select * from DM_CNDV_SUDUNG p\n" + "  where ((:rq_ma is not null and p.MA LIKE '%' || :rq_ma ||'%') or :rq_ma is null)\n" + "  and ((:rq_ma is not null and p.TEN_NGAN LIKE '%' || :rq_tenNgan ||'%') or :rq_tenNgan is null)\n" + "  and ((:rq_tenDayDu is not null and p.TEN_DAY_DU LIKE '%' || :rq_tenDayDu ||'%') or :rq_tenDayDu is null)\n" + "  and ((:rq_diaChi is not null and p.DIA_CHI_CN LIKE '%' || :rq_diaChi ||'%') or :rq_diaChi is null)\n" + "  and ((:rq_maSoThue is not null and p.MA_SO_THUE LIKE '%' || :rq_maSoThue ||'%') or :rq_maSoThue is null)\n" + "  and ((:rq_lienHeDienToan is not null and p.LIEN_HE_DIEN_TOAN LIKE '%' || :rq_lienHeDienToan ||'%') or :rq_lienHeDienToan is null)", SuDung.class)
                .setParameter("rq_tenNgan", suDung.getTenNgan())
                .setParameter("rq_tenDayDu", suDung.getTenDayDu())
                .setParameter("rq_diaChi", suDung.getDiaChi())
                .setParameter("rq_maSoThue", suDung.getMaSoThue())
                .setParameter("rq_ma", suDung.getMa())
                .setParameter("rq_lienHeDienToan", suDung.getLienHeDienToan())
                .setFirstResult(limit*currentPage)
                .setMaxResults(limit)
                .getResultList();
        List<SuDung> allRecord = entityManager.createNativeQuery("select * from DM_CNDV_SUDUNG p\n" + "  where ((:rq_ma is not null and p.MA LIKE '%' || :rq_ma ||'%') or :rq_ma is null)\n" + "  and ((:rq_ma is not null and p.TEN_NGAN LIKE '%' || :rq_tenNgan ||'%') or :rq_tenNgan is null)\n" + "  and ((:rq_tenDayDu is not null and p.TEN_DAY_DU LIKE '%' || :rq_tenDayDu ||'%') or :rq_tenDayDu is null)\n" + "  and ((:rq_diaChi is not null and p.DIA_CHI_CN LIKE '%' || :rq_diaChi ||'%') or :rq_diaChi is null)\n" + "  and ((:rq_maSoThue is not null and p.MA_SO_THUE LIKE '%' || :rq_maSoThue ||'%') or :rq_maSoThue is null)\n" + "  and ((:rq_lienHeDienToan is not null and p.LIEN_HE_DIEN_TOAN LIKE '%' || :rq_lienHeDienToan ||'%') or :rq_lienHeDienToan is null)", SuDung.class)
                .setParameter("rq_tenNgan", suDung.getTenNgan())
                .setParameter("rq_tenDayDu", suDung.getTenDayDu())
                .setParameter("rq_diaChi", suDung.getDiaChi())
                .setParameter("rq_maSoThue", suDung.getMaSoThue())
                .setParameter("rq_ma", suDung.getMa())
                .setParameter("rq_lienHeDienToan", suDung.getLienHeDienToan())
                .getResultList();

        return new PagingResponse(records, allRecord.size());
    }

    public List<SuDung> findByCriteria(SuDung suDung) {
        return suDungRepository.findAll(new Specification<SuDung>() {
            @Override
            public Predicate toPredicate(Root<SuDung> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (suDung.getMa() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA), "%" + suDung.getMa() + "%")));
                }

                if (suDung.getTenNgan() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.TEN_NGAN), "%" + suDung.getTenNgan() + "%")));
                }

                if (suDung.getTenDayDu() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.TEN_DAY_DU), "%" + suDung.getTenDayDu() + "%")));
                }

                if (suDung.getDiaChi() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.DIA_CHI), "%" + suDung.getDiaChi() + "%")));
                }

                if (suDung.getMaSoThue() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA_SO_THUE), "%" + suDung.getMaSoThue() + "%")));
                }
                if (suDung.getLienHeDienToan() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.LIEN_HE_DIEN_TOAN), "%" + suDung.getLienHeDienToan() + "%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        });
    }

    public void addSuDung(SuDung suDung) {
        suDungRepository.save(suDung);
    }

    public void deleteSuDungById(Integer id) {
        suDungRepository.deleteById(id);
    }

//    public void page
    public PagingResponse findByPaging(SuDung suDung, Pageable pageable) {
        Page<SuDung> page = suDungRepository.findAll(new Specification<SuDung>() {
            @Override
            public Predicate toPredicate(Root<SuDung> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (suDung.getMa() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA), "%" + suDung.getMa() + "%")));
                }

                if (suDung.getTenNgan() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.TEN_NGAN), "%" + suDung.getTenNgan() + "%")));
                }

                if (suDung.getTenDayDu() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.TEN_DAY_DU), "%" + suDung.getTenDayDu() + "%")));
                }

                if (suDung.getDiaChi() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.DIA_CHI), "%" + suDung.getDiaChi() + "%")));
                }

                if (suDung.getMaSoThue() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA_SO_THUE), "%" + suDung.getMaSoThue() + "%")));
                }
                if (suDung.getLienHeDienToan() != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.LIEN_HE_DIEN_TOAN), "%" + suDung.getLienHeDienToan() + "%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, pageable);
        return new PagingResponse(page.getContent(), page.getTotalElements());
    }
}
