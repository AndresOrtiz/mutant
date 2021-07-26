package com.mercado.mutant.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercado.mutant.model.Stats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {

    @JsonProperty("count_mutant_dna")
    private long countMutantDna;
    @JsonProperty("count_human_dna")
    private long countHumanDna;
    private double ratio;

    public static StatsResponse fromModel(Stats stats) {
        return StatsResponse.builder()
                .countHumanDna(stats.getCountHumanDna())
                .countMutantDna(stats.getCountMutantDna())
                .ratio(stats.getRatio())
                .build();
    }

}
