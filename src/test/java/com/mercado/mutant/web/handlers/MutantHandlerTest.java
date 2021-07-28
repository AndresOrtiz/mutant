package com.mercado.mutant.web.handlers;

import com.mercado.mutant.model.DnaSequence;
import com.mercado.mutant.model.Stats;
import com.mercado.mutant.services.HumanService;
import com.mercado.mutant.services.StatsService;
import com.mercado.mutant.web.request.DnaRequest;
import com.mercado.mutant.web.response.StatsResponse;
import com.mercado.mutant.web.routers.MutantRouterConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {MutantRouterConfig.class, MutantHandler.class})
class MutantHandlerTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private HumanService humanService;

    @MockBean
    private StatsService statsService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void saveHumanOk() {
        when(humanService.calculateAndSaveHuman(any(DnaSequence.class))).thenReturn(Mono.just(true));

        webTestClient.post()
                .uri(MutantRouterConfig.MUTANT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(DnaRequest.builder().dna(newArrayList("A")).build()), DnaRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void saveHumanForbidden() {
        when(humanService.calculateAndSaveHuman(any(DnaSequence.class))).thenReturn(Mono.just(false));

        webTestClient.post()
                .uri(MutantRouterConfig.MUTANT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(DnaRequest.builder().dna(newArrayList("A")).build()), DnaRequest.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void saveHumanBadRequest() {
        when(humanService.calculateAndSaveHuman(any(DnaSequence.class))).thenReturn(Mono.just(true));

        webTestClient.post()
                .uri(MutantRouterConfig.MUTANT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(DnaRequest.builder().build()), DnaRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getStatsOk() {
        when(statsService.getStats())
                .thenReturn(Mono.just(Stats.builder().countMutantDna(1L).countHumanDna(1L).ratio(1).build()));

        webTestClient.get()
                .uri(MutantRouterConfig.STATS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatsResponse.class)
                .value(statsResponse -> {
                    assertThat(statsResponse.getCountHumanDna()).isEqualTo(1L);
                    assertThat(statsResponse.getCountMutantDna()).isEqualTo(1L);
                    assertThat(statsResponse.getRatio()).isEqualTo(1);
                });
    }

    @Test
    void getStatsNotFound() {
        when(statsService.getStats()).thenReturn(Mono.empty());

        webTestClient.get()
                .uri(MutantRouterConfig.STATS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

}