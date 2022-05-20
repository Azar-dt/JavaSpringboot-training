package edu.ftv.training.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ftv.training.DAO.impl.SuDungJDBCTemplate;
import edu.ftv.training.Model.PagingRequest;
import edu.ftv.training.Model.PagingResponse;
import edu.ftv.training.Model.ResponseMessage;
import edu.ftv.training.Model.SuDung;
import edu.ftv.training.Service.SuDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class SuDungController {
    @Autowired
    private SuDungService suDungService;

    @Autowired
    private SuDungJDBCTemplate suDungJDBCTemplate;

    @GetMapping("/sudung")
    public List<SuDung> findAll() {
        return suDungService.findAllSuDung();
    }


    // delete
    @DeleteMapping("/sudung/{id}")
    public ResponseEntity<ResponseMessage> deleteSuDungById(@PathVariable(name = "id") Integer id) {
        suDungService.deleteSuDungById(id);
        return ResponseEntity.ok(new ResponseMessage(200, true, "deleted successfully"));
    }

    // add and edit
    @PostMapping("/sudung")
    public ResponseEntity<ResponseMessage> addSuDung(@RequestBody SuDung suDung) {
        suDungService.addSuDung(suDung);
        return ResponseEntity.ok(new ResponseMessage(200, true, "created successfully"));
    }

    // search
    @PostMapping("/sudung/search")
    public List<SuDung> findByCriteria(@RequestBody SuDung suDung) {
        return suDungService.findByCriteria(suDung);
    }

    @PostMapping("/sudung/procedure")
    public PagingResponse findById(@RequestBody PagingRequest pagingRequest) throws JsonProcessingException {
        return suDungJDBCTemplate.getRecord(pagingRequest);
    }

    @PostMapping("/sudung/nativequery")
    public PagingResponse searchByNativeQuery(@RequestBody PagingRequest pagingRequest) {
        return suDungService.searchByNativeQuery(pagingRequest);
    }

    @PostMapping("/sudung/paging")
    public PagingResponse findByPaging(@RequestBody PagingRequest pagingRequest){
        return suDungService.findByPaging(pagingRequest.getSuDung(), PageRequest.of(pagingRequest.getCurrentPage(),pagingRequest.getLimit()));
    }
}
