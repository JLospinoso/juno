/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import net.lospi.juno.NotImplementedException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;

@Test(groups="unit")
public class ColtBooleanDistributionTest {
    private CachedNaturalLogarithm naturalLogarithm;
    private RandomEngine engine;
    int size;

    @DataProvider
    Object[][] inputs(){
        return new Object[][]{
                new Object[]{ Math.log(0.0) },
                new Object[]{ Math.log(0.3) },
                new Object[]{ Math.log(0.5) },
                new Object[]{ Math.log(0.7) },
                new Object[]{ Math.log(1.0) }
        };
    }

    @BeforeMethod
    public void setUp(){
        naturalLogarithm = new CachedNaturalLogarithm();
        engine = new MersenneTwister(0);
        size = 10000;
    }

    @Test(dataProvider = "inputs")
    public void testNextWithLogProbability(Double logProbability) throws Exception {
        ColtBooleanDistribution coltBooleanDistribution = new ColtBooleanDistribution(engine, naturalLogarithm);
        double expectedValue = Math.exp(logProbability);
        int sum = 0;
        int iteration = 0;
        while(size > iteration++){
            boolean result = coltBooleanDistribution.nextWithLogProbability(logProbability);
            if(result){
                sum++;
            }
        }
        double average = (double)sum / (double)size;
        assertThat(Math.abs(average - expectedValue)).isLessThan(0.01);
    }
}
