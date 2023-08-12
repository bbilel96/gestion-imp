package tn.iit.service.micro.utilclass;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RequestValidation {
    private String field;
    private String errorMessage;

}
