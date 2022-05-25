package edu.ftv.training.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ftv.training.payload.PagingRequest;
import edu.ftv.training.payload.PagingResponse;

public interface SuDungDAO {
    public PagingResponse getRecord(PagingRequest pagingRequest) throws JsonProcessingException;
}
