package tn.iit.service.micro.app.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import javax.validation.groups.Default;
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response reponse) {
        log.error("{}", reponse);
        return defaultErrorDecoder.decode(invoqueur, reponse);
    }
}
