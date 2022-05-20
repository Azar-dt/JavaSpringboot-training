package edu.ftv.training.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ftv.training.Model.PagingRequest;
import edu.ftv.training.Model.PagingResponse;

public interface SuDungDAO {
    public PagingResponse getRecord(PagingRequest pagingRequest) throws JsonProcessingException;
}
