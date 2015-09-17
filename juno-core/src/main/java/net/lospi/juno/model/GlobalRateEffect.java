package net.lospi.juno.model;

public class GlobalRateEffect implements RateEffect {
    private static final int HASH_CODE = GlobalRateEffect.class.hashCode();

    private final String aspect;

    public GlobalRateEffect(String aspect) {
        this.aspect = aspect;
    }

    @Override
    public String toString() {
        return String.format("Rate (%s)", aspect);
    }


    @Override
    public int compareTo(Effect o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalRateEffect effect = (GlobalRateEffect) o;

        if (!aspect.equals(effect.aspect)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return HASH_CODE;
    }
}
