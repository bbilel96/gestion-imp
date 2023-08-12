package tn.iit.service.micro.utilclass;

import lombok.*;
import tn.iit.service.micro.utilenum.ResponseBehavior;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ResponseMessage {
    private String message;
    @EqualsAndHashCode.Include
    private ResponseBehavior behavior;
    private List<RequestValidation> validations = new ArrayList<>();

    public ResponseMessage(String message, ResponseBehavior behavior) {
        this.message = message;
        this.behavior = behavior;
    }


}
