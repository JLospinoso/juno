/*
 * Copyright (c) 2014. Josh Lospinoso. All rights reserved
 */

package net.lospi.juno.estimation.sim;

import net.lospi.juno.estimation.calc.LikelihoodDerivativesCalculator;
import net.lospi.juno.estimation.elements.LikelihoodDerivatives;
import net.lospi.juno.model.CommonsModelBuilder;
import net.lospi.juno.model.Model;
import net.lospi.juno.model.ObjectVector;

import java.util.List;

public class SerialScoreSimulator implements ScoreSimulator {
    private static final int DEFAULT_DRAWS_PER_SAMPLE = 1;
    private final LikelihoodDerivativesSimulator likelihoodDerivativesSimulator;
    private final LikelihoodDerivativesCalculator likelihoodDerivativesCalculator;
    private final CommonsModelBuilder commonsModelBuilder;
    private int samplingInterval;
    private Model model;
    private int drawsPerSample;

    public SerialScoreSimulator(LikelihoodDerivativesSimulator likelihoodDerivativesSimulator, LikelihoodDerivativesCalculator likelihoodDerivativesCalculator,
                                CommonsModelBuilder commonsModelBuilder) {
        this.likelihoodDerivativesSimulator = likelihoodDerivativesSimulator;
        this.likelihoodDerivativesCalculator = likelihoodDerivativesCalculator;
        this.commonsModelBuilder = commonsModelBuilder;
        drawsPerSample = DEFAULT_DRAWS_PER_SAMPLE;
    }

    @Override
    public ObjectVector sample() {
        checkFields();
        likelihoodDerivativesSimulator.setSamplingInterval(samplingInterval);
        List<LikelihoodDerivatives> derivatives = likelihoodDerivativesSimulator.simulateLikelihoodDerivatives(model, drawsPerSample);
        LikelihoodDerivatives observedDerivatives = likelihoodDerivativesCalculator.calculate(derivatives, model.getAllEffects());
        ObjectVector result = observedDerivatives.getScore();
        return result.mapMultiply(-1D);
    }

    @Override
    public ObjectVector addToAndSetParameters(ObjectVector step) {
        checkFields();
        ObjectVector currentParameter = model.getAllEffectsParameter();
        ObjectVector newParameter = currentParameter.add(step);
        setParameters(newParameter);
        return newParameter;
    }

    @Override
    public List getIndex() {
        return model.getAllEffects();
    }

    @Override
    public int parameterSize() {
        checkFields();
        return this.model.getAllEffectsSize();
    }

    @Override
    public void setParameters(ObjectVector newParameter) {
        model = commonsModelBuilder.newModel()
                .from(model)
                .withNewParameter(newParameter)
                .build();
    }

    @Override
    public void setSamplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return this.model;
    }

    public void setDrawsPerSample(int drawsPerSample) {
        this.drawsPerSample = drawsPerSample;
    }

    private void checkFields() {
        if(model == null) {
            throw new IllegalStateException("Model was not set");
        }
    }
}
