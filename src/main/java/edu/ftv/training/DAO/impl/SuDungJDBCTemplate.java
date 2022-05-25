package edu.ftv.training.DAO.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ftv.training.DAO.SuDungDAO;
import edu.ftv.training.Model.SuDung;
import edu.ftv.training.payload.PagingRequest;
import edu.ftv.training.payload.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class SuDungJDBCTemplate implements SuDungDAO {
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public PagingResponse getRecord(PagingRequest pagingRequest) throws JsonProcessingException {
        SuDung suDung = pagingRequest.getSuDung();
        int limit = pagingRequest.getLimit();
        int currentPage = pagingRequest.getCurrentPage();
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(suDung);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplateObject).withCatalogName("PCK_DM_CDNV_SUDUNG").withProcedureName("GET_ALL");
        SqlParameterSource in = new MapSqlParameterSource().addValue("i_data", data).addValue("i_limit", limit).addValue("i_currentPage", currentPage);
//        System.out.println(data);
        Map<String, Object> out = jdbcCall.execute(in);

        List<SuDung> sd = (List<SuDung>) out.get("CUR");
        int total =((BigDecimal) out.get("O_TOTAL")).intValue();
        System.out.println(total);
        return new PagingResponse(sd,total);
    }
}
