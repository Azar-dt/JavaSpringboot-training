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
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
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

    public PagingResponse pagingByNativeQuery(PagingRequest pagingRequest) {
        SuDung suDung = pagingRequest.getSuDung();
        int limit = pagingRequest.getLimit();
        int currentPage = pagingRequest.getCurrentPage();
//        limit*currentPage
        StringBuilder sql = new StringBuilder("select * from DM_CNDV_SUDUNG where 1=1 ");
        HashMap<String, String> params = new HashMap<>();

        if (suDung.getMa() != null && !suDung.getMa().isEmpty()) {
            sql.append(" and MA like :ma");
            params.put("ma", "%" + suDung.getMa() + "%");
        }
        if (suDung.getDiaChi() != null && !suDung.getDiaChi().isEmpty()) {
            sql.append(" and lower(DIA_CHI_CN) like :diaChi");
            params.put("diaChi", "%" + suDung.getDiaChi().toLowerCase() + "%");
        }
        if (suDung.getLienHeDienToan() != null && !suDung.getLienHeDienToan().isEmpty()) {
            sql.append(" and LIEN_HE_DIEN_TOAN like :lienHeDienToan");
            params.put("lienHeDienToan", "%" + suDung.getLienHeDienToan() + "%");
        }
        if (suDung.getTenNgan() != null && !suDung.getTenNgan().isEmpty()) {
            sql.append(" and lower(TEN_NGAN) like :tenNgan");
            params.put("tenNgan", "%" + suDung.getTenNgan().toLowerCase() + "%");
        }
        if(suDung.getTenDayDu() != null && !suDung.getTenDayDu().isEmpty()){
            sql.append(" and lower(TEN_DAY_DU) like :tenDayDu");
            params.put("tenDayDu", "%" + suDung.getTenDayDu().toLowerCase() + "%");
        }
        if(suDung.getMaSoThue() != null && !suDung.getMaSoThue().isEmpty()){
            sql.append(" and MA_SO_THUE like :maSoThue");
            params.put("maSoThue", "%" + suDung.getMaSoThue() + "%");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), suDung.getClass());
        //for set parameter
        for (String key : params.keySet()) {
            System.out.println(key + params.get(key));
            query.setParameter(key, params.get(key));
        }
//        Query totalQuery = query;
        int total = query.getResultList().size();
        query.setFirstResult(limit*currentPage);
        query.setMaxResults(limit);
        List<SuDung> records = query.getResultList();
        System.out.println(records);

        //count


        return new PagingResponse(records,total);
    }

    public void addSuDung(SuDung suDung) {
        suDungRepository.save(suDung);
    }

    public void deleteSuDungById(Integer id) {
        suDungRepository.deleteById(id);
    }

    public List<SuDung> findByCriteria(SuDung suDung) {
        return suDungRepository.findAll(new Specification<SuDung>() {
            @Override
            public Predicate toPredicate(Root<SuDung> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (suDung.getMa() != null && !suDung.getMa().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA), "%" + suDung.getMa() + "%")));
                }

                if (suDung.getTenNgan() != null && !suDung.getTenNgan().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(SuDung_.TEN_NGAN)), "%" + suDung.getTenNgan()+ "%")));
                }

                if (suDung.getTenDayDu() != null && !suDung.getTenDayDu().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(SuDung_.TEN_DAY_DU)), "%" + suDung.getTenDayDu() + "%")));
                }

                if (suDung.getDiaChi() != null && !suDung.getDiaChi().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(SuDung_.DIA_CHI)), "%" + suDung.getDiaChi() + "%")));
                }

                if (suDung.getMaSoThue() != null && !suDung.getMaSoThue().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA_SO_THUE), "%" + suDung.getMaSoThue() + "%")));
                }
                if (suDung.getLienHeDienToan() != null && !suDung.getLienHeDienToan().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.LIEN_HE_DIEN_TOAN), "%" + suDung.getLienHeDienToan() + "%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        });
    }

//    public void page
    public PagingResponse findByPaging(SuDung suDung, Pageable pageable) {
        Page<SuDung> page = suDungRepository.findAll(new Specification<SuDung>() {
            @Override
            public Predicate toPredicate(Root<SuDung> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (suDung.getMa() != null && !suDung.getMa().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA), "%" + suDung.getMa() + "%")));
                }

                if (suDung.getTenNgan() != null && !suDung.getTenNgan().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(SuDung_.TEN_NGAN)), "%" + suDung.getTenNgan()+ "%")));
                }

                if (suDung.getTenDayDu() != null && !suDung.getTenDayDu().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(SuDung_.TEN_DAY_DU)), "%" + suDung.getTenDayDu() + "%")));
                }

                if (suDung.getDiaChi() != null && !suDung.getDiaChi().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(SuDung_.DIA_CHI)), "%" + suDung.getDiaChi() + "%")));
                }

                if (suDung.getMaSoThue() != null && !suDung.getMaSoThue().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.MA_SO_THUE), "%" + suDung.getMaSoThue() + "%")));
                }
                if (suDung.getLienHeDienToan() != null && !suDung.getLienHeDienToan().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(SuDung_.LIEN_HE_DIEN_TOAN), "%" + suDung.getLienHeDienToan() + "%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, pageable);
        return new PagingResponse(page.getContent(), page.getTotalElements());
    }
}
