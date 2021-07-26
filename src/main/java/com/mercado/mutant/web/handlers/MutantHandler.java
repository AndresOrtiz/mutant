package com.mercado.mutant.web.handlers;

import com.mercado.mutant.services.HumanService;
import com.mercado.mutant.services.StatsService;
import com.mercado.mutant.web.request.DnaRequest;
import com.mercado.mutant.web.response.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static java.util.Optional.of;

@Component
@RequiredArgsConstructor
public class MutantHandler {

    private final HumanService humanService;
    private final StatsService statsService;
    private final Validator validator;

    public Mono<ServerResponse> calculateAndSaveHuman(ServerRequest request) {
        return request.bodyToMono(DnaRequest.class)
                .doOnNext(this::validate)
                .map(DnaRequest::toModel)
                .flatMap(humanService::calculateAndSaveHuman)
                .flatMap(isMutant -> of(ServerResponse.ok().build())
                        .filter(serverResponseMono -> isMutant)
                        .orElse(ServerResponse.status(HttpStatus.FORBIDDEN).build()));
    }

    public Mono<ServerResponse> getStats(ServerRequest request) {
        return statsService.getStats()
                .map(StatsResponse::fromModel)
                .flatMap(statsResponse -> ServerResponse.ok().bodyValue(statsResponse))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private void validate(DnaRequest dnaRequest) {
        Errors errors = new BeanPropertyBindingResult(dnaRequest, "dnaRequest");
        validator.validate(dnaRequest, errors);

        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }

}
