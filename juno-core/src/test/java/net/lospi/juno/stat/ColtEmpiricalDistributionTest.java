/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class ColtEmpiricalDistributionTest {
    private Map<Character, Double> pdf;
    private RandomEngine engine;
    private int seed = 0;
    int size = 10000;
    double expectedACount, expectedBCount, expectedCCount;

    @BeforeMethod
    public void setUp(){
        engine = new MersenneTwister(seed);
        pdf = ImmutableMap.of(
                'A', .1,
                'B', .2,
                'C', .7
        );
        expectedACount = size * pdf.get('A');
        expectedBCount = size * pdf.get('B');
        expectedCCount = size * pdf.get('C');
    }

    public void integrationTest() throws Exception {
        ColtEmpiricalDistribution<Character> distribution = new ColtEmpiricalDistribution<Character>(pdf, engine);
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        while(size-->0){
            char sample = distribution.next();
            switch (sample){
                case 'A':
                    aCount++;
                    break;
                case 'B':
                    bCount++;
                    break;
                case 'C':
                    cCount++;
                    break;
                default:
                    throw new IllegalStateException("Unknown sample: " + sample);
            }
        }
        assertThat(Math.abs(aCount - expectedACount)).isLessThan(50);
        assertThat(Math.abs(bCount - expectedBCount)).isLessThan(50);
        assertThat(Math.abs(cCount - expectedCCount)).isLessThan(50);
    }
}
