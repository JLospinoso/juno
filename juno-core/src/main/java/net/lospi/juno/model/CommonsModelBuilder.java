package net.lospi.juno.model;

import com.google.common.collect.ImmutableList;

import java.util.*;

public class CommonsModelBuilder {
    public Stub newModel() {
        return new Stub();
    }

    public class Stub {
        private Map<AlterSelectionEffect, Double> alterSelectionEffect = null;
        private Map<RateEffect, Double> rateEffects = null;
        private RateEffect eff = new GlobalRateEffect(""); // TODO: Get rid of global rate concept
        private Model model;
        private ObjectVector newParameter;

        private Stub() {

        }

        public Stub withEffect(AlterSelectionEffect effect, double parameter) {
            if(alterSelectionEffect == null) {
                alterSelectionEffect = new HashMap<AlterSelectionEffect, Double>();
            }
            alterSelectionEffect.put(effect, parameter);
            return this;
        }

        public Stub withGlobalRate(double globalRate) {
            if(rateEffects == null) {
                rateEffects = new HashMap<RateEffect, Double>();
            }
            rateEffects.put(eff, globalRate);
            return this;
        }

        public Model build() {
            if(newModelValid() && copyModelValid()) {
                throw new IllegalStateException("You cannot set both a new model and a copy model.");
            }
            if(newModelValid()) {
                return newModel();
            }
            if(copyModelValid()) {
                return copyModel();
            }
            if(rateOnlyModelValid()) {
                return rateOnlyModel();
            }
            throw new IllegalStateException("You must set either a new model or a copy model.");
        }

        private boolean newModelValid() {
            return alterSelectionEffect != null && rateEffects != null;
        }

        private boolean rateOnlyModelValid() {
            return rateEffects != null;
        }

        private boolean copyModelValid() {
            return model != null && newParameter != null;
        }

        private Model newModel() {
            List<AlterSelectionEffect> alterSelectionEffectsList = new ArrayList(alterSelectionEffect.keySet());
            List<RateEffect> rateEffectsList = new ArrayList(rateEffects.keySet());
            Collections.sort(alterSelectionEffectsList);
            Collections.sort(rateEffectsList);

            List<Effect> allEffects = new ArrayList<Effect>(rateEffectsList);
            allEffects.addAll(alterSelectionEffectsList);

            double[] allEffectsParameterArray = new double[allEffects.size()];
            for(int i=0; i<rateEffects.size(); i++) {
                RateEffect effect = rateEffectsList.get(i);
                allEffectsParameterArray[i] = rateEffects.get(effect);
            }
            for(int i=0; i<alterSelectionEffectsList.size(); i++) {
                AlterSelectionEffect effect = alterSelectionEffectsList.get(i);
                allEffectsParameterArray[rateEffectsList.size() + i] = alterSelectionEffect.get(effect);
            }
            ObjectVector allEffectsParameter = new SafeObjectVector(allEffectsParameterArray, allEffects);
            return new CommonsModel(rateEffectsList, alterSelectionEffectsList, allEffectsParameter);
        }

        private Model copyModel() {
            return new CommonsModel(model.getRateEffects(), model.getAlterSelectionEffects(), newParameter);
        }

        private Model rateOnlyModel() {
            List<RateEffect> rateEffectsList = new ArrayList(rateEffects.keySet());
            Collections.sort(rateEffectsList);
            List<Effect> allEffects = new ArrayList<Effect>(rateEffectsList);
            double[] allEffectsParameterArray = new double[allEffects.size()];
            for(int i=0; i<rateEffects.size(); i++) {
                RateEffect effect = rateEffectsList.get(i);
                allEffectsParameterArray[i] = rateEffects.get(effect);
            }
            ObjectVector allEffectsParameter = new SafeObjectVector(allEffectsParameterArray, allEffects);
            return new CommonsModel(rateEffectsList, new ArrayList<AlterSelectionEffect>(), allEffectsParameter);
        }

        public Stub from(Model model) {
            this.model = model;
            return this;
        }

        public Stub withNewParameter(ObjectVector newParameter) {
            this.newParameter = newParameter;
            return this;
        }
    }
}
