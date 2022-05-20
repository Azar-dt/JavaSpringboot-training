package edu.ftv.training.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {
    private int statusCode;
    private boolean isSuccess;
    private String message;
}
