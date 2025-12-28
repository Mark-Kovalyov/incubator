package mayton.genetic.jenetic;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

public class DemoJenetic {


    static Integer fitness(Genotype<BitGene> genotype) {
        BitChromosome chromosome = (BitChromosome) genotype.chromosome();
        return (int) chromosome.stream()
                .map(BitGene::allele)
                .filter(b -> b)
                .count();
    }

    // ----- CONFIG -----
    private static final int BIT_LENGTH = 64;
    private static final int POPULATION_SIZE = 200;


    private static final int MAX_GENERATIONS = 100;

    static void main(String[] args) throws Exception {
        // Genotype factory
        Factory<Genotype<BitGene>> genotypeFactory = Genotype.of(BitChromosome.of(BIT_LENGTH, 0.5));

        // Engine configuration
        Engine<BitGene, Integer> engine = Engine.builder(
                        DemoJenetic::fitness,
                        genotypeFactory
                )
                .populationSize(POPULATION_SIZE)
                .selector(new EliteSelector<>(3))        // elitism
                .alterers(
                        new SinglePointCrossover<>(0.3), // crossover probability
                        new Mutator<>(0.01)               // mutation probability
                )
                .build();

        // Run evolution
        Phenotype<BitGene, Integer> best = engine.stream()
                .limit(MAX_GENERATIONS)
                .collect(EvolutionResult.toBestPhenotype());

        // Output result
        System.out.println("Best fitness: " + best.fitness());
        System.out.println("Best genotype: " + best.genotype());
    }

}
