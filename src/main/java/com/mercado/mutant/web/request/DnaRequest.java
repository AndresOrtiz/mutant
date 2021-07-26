package com.mercado.mutant.web.request;

import com.mercado.mutant.model.DnaSequence;
import com.mercado.mutant.web.validation.constraints.Dna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DnaRequest {

    @Dna
    @NotEmpty
    private List<String> dna;

    public static DnaSequence toModel(DnaRequest dnaRequest) {
        return DnaSequence.builder().dna(dnaRequest.getDna()).build();
    }

}
