package com.mercado.mutant.web.routers;

import com.mercado.mutant.services.HumanService;
import com.mercado.mutant.services.StatsService;
import com.mercado.mutant.web.handlers.MutantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MutantRouterConfig {

    public static final String MUTANT_URL = "/api/v1/mutant";
    public static final String STATS_URL = "/api/v1/stats";

    @RouterOperations({
            @RouterOperation(path = MUTANT_URL, beanClass = HumanService.class, beanMethod = "calculateAndSaveHuman",
                    operation = @Operation(operationId = "mutant",
                            summary = "Find out if a DNA sequence is mutant and save it",
                            tags = {"Mutant"},
                            responses = {@ApiResponse(responseCode = "200", description = "Dna is mutant"),
                                    @ApiResponse(responseCode = "403", description = "Dna is not mutant")})),
            @RouterOperation(path = STATS_URL, beanClass = StatsService.class, beanMethod = "getStats",
                    operation = @Operation(operationId = "stats",
                            summary = "Get total stats",
                            tags = {"Stats"},
                            responses = {@ApiResponse(responseCode = "200", description = "Successful operation")}))})
    @Bean
    public RouterFunction<ServerResponse> mutantRoutes(MutantHandler mutantHandler) {
        return route()
                .POST(MUTANT_URL, accept(APPLICATION_JSON), mutantHandler::calculateAndSaveHuman)
                .GET(STATS_URL, accept(APPLICATION_JSON), mutantHandler::getStats)
                .build();
    }

}
