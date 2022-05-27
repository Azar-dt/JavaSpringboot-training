package edu.ftv.training.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.istack.NotNull;
import edu.ftv.training.DAO.impl.SuDungJDBCTemplate;
import edu.ftv.training.Model.CustomUserDetails;
import edu.ftv.training.Model.SuDung;
import edu.ftv.training.Service.CustomUserDetailService;
import edu.ftv.training.Service.SuDungService;
import edu.ftv.training.payload.PagingRequest;
import edu.ftv.training.payload.PagingResponse;
import edu.ftv.training.payload.ResponseMessage;
import edu.ftv.training.payload.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class SuDungController {
    @Autowired
    private SuDungService suDungService;

    @Autowired
    private SuDungJDBCTemplate suDungJDBCTemplate;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/token/refresh")
    public ResponseEntity refreshAccessToken(@NotNull @RequestBody Token token, HttpServletRequest request) {
            String refreshToken =token.getRefreshToken();
            if (refreshToken == null) return ResponseEntity.badRequest().body("bad refreshToken");
            Algorithm algorithm = Algorithm.HMAC256("baka".getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
            String username = decodedJWT.getSubject();
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailService.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                String accessToken = JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);

                return ResponseEntity.ok(new Token(accessToken, refreshToken));
            } else {
                return ResponseEntity.status(403).body("Unauthorized !!!");
            }
    }

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
        return suDungService.pagingByNativeQuery(pagingRequest);
    }

    @PostMapping("/sudung/paging")
    public PagingResponse findByPaging(@RequestBody PagingRequest pagingRequest){
        return suDungService.findByPaging(pagingRequest.getSuDung(), PageRequest.of(pagingRequest.getCurrentPage(),pagingRequest.getLimit()));
    }
}
