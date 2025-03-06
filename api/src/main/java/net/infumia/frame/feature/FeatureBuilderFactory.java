package net.infumia.frame.feature;

public interface FeatureBuilderFactory<O> {
    O install(Class<? extends Feature> feature);

    O install(Feature feature);
}
