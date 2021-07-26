package com.mercado.mutant.services.impl;

import com.mercado.mutant.services.DnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DnaServiceImpl implements DnaService {

    private final static int[] rowAdvance = {0, 1, 1};
    private final static int[] columnAdvance = {1, 1, 0};
    private static final int MUTANT_SEQUENCE_LENGTH = 4;
    private static final int DIRECTIONS = 3;

    @Override
    public boolean lookForMutantDna(List<String> dna) {
        boolean isMutant = false;
        for (int i = 0; i < dna.size() && !isMutant; i++) {
            for (int j = 0; j < dna.get(0).length() && !isMutant; j++) {
                for (int k = 0; k < DIRECTIONS && !isMutant; k++) {
                    isMutant = areThereEqualCharacters(dna, dna.get(i).charAt(j), i + rowAdvance[k],
                            j + columnAdvance[k], k, 1);
                }
            }
        }
        return isMutant;
    }

    private boolean areThereEqualCharacters(List<String> dna, char current, int row, int col, int dir, int count) {
        if (count == MUTANT_SEQUENCE_LENGTH) {
            return true;
        }
        if (row < dna.size() && col < dna.get(0).length() && dna.get(row).charAt(col) == current) {
            return areThereEqualCharacters(dna, current, row + rowAdvance[dir], col + columnAdvance[dir], dir,
                    count + 1);
        }
        return false;
    }

}
