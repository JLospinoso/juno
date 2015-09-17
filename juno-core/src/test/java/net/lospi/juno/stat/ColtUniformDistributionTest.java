/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.stat;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

@Test(groups="unit")
public class ColtUniformDistributionTest {
    private RandomEngine engine;
    private int seed = 0;
    int size;
    double expectedCount;

    @BeforeMethod
    public void setUp(){
        size = 10000;
        engine = new MersenneTwister(seed);
        expectedCount = size / 3d;
    }

    public void integrationTestNext() throws Exception {
        ColtUniformDistribution distribution = new ColtUniformDistribution(engine);
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        while(size-->0){
            int sample = distribution.next(1, 3);
            switch (sample){
                case 1:
                    count1++;
                    break;
                case 2:
                    count2++;
                    break;
                case 3:
                    count3++;
                    break;
                default:
                    throw new IllegalStateException("Unknown sample: " + sample);
            }
        }
        assertThat(Math.abs(count1 - expectedCount)).isLessThan(50);
        assertThat(Math.abs(count2 - expectedCount)).isLessThan(50);
        assertThat(Math.abs(count3 - expectedCount)).isLessThan(50);
    }

    public void integrationTestNextList() throws Exception {
        ColtUniformDistribution distribution = new ColtUniformDistribution(engine);
        List<Character> support = ImmutableList.of('A', 'B', 'C');
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        while(size-->0){
            char sample = distribution.next(support);
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
        assertThat(Math.abs(aCount - expectedCount)).isLessThan(50);
        assertThat(Math.abs(bCount - expectedCount)).isLessThan(50);
        assertThat(Math.abs(cCount - expectedCount)).isLessThan(50);
    }

    public void integrationNextDouble() throws Exception {
        ColtUniformDistribution distribution = new ColtUniformDistribution(engine);
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int index = 0;
        while(size>index++){
            double next = distribution.nextContinuous(-10d, 10d);
            sum += next;
            if(min > next){
                min = next;
            }
            if(max < next){
                max = next;
            }
        }
        double mean = sum / (double)size;
        assertThat(max).isLessThanOrEqualTo(10);
        assertThat(min).isGreaterThanOrEqualTo(-10);
        assertThat(Math.abs(mean)).isLessThanOrEqualTo(.5);
    }

    public void nextNotEqualTo(){
        ColtUniformDistribution distribution = new ColtUniformDistribution(engine);
        List<Character> support = ImmutableList.of('A', 'B', 'C', 'D');
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        int dCount = 0;
        while(size-->0){
            char sample = distribution.nextNotEqualTo('B', support);
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
                case 'D':
                    dCount++;
                    break;
                default:
                    throw new IllegalStateException("Unknown sample: " + sample);
            }
        }
        assertThat(Math.abs(aCount - expectedCount)).isLessThan(50);
        assertThat(bCount).isZero();
        assertThat(Math.abs(cCount - expectedCount)).isLessThan(50);
        assertThat(Math.abs(dCount - expectedCount)).isLessThan(50);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "List must contain more than one element\\.")
    public void exceptionRejectionSampleOneElementList(){
        ColtUniformDistribution distribution = new ColtUniformDistribution(engine);
        distribution.nextNotEqualTo("hello", ImmutableList.of("hello"));
    }
}
