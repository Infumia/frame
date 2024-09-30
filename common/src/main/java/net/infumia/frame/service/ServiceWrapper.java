package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import java.util.function.Predicate;
import net.infumia.frame.service.exception.PipelineException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ServiceWrapper<Context, Result> implements Comparable<ServiceWrapper<Context, Result>> {

    private final TypeToken<? extends Service<Context, Result>> serviceType;
    private final Collection<Predicate<Context>> filters;
    final Service<Context, Result> implementation;
    final boolean defaultImplementation;

    ServiceWrapper(
        @NotNull final TypeToken<? extends Service<Context, Result>> serviceType,
        @NotNull final Service<Context, Result> implementation,
        final boolean defaultImplementation,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        this.serviceType = serviceType;
        this.implementation = implementation;
        this.defaultImplementation = defaultImplementation;
        this.filters = filters;
    }

    boolean passes(@NotNull final Context context) {
        if (this.defaultImplementation || this.filters == null) {
            return true;
        }
        for (final Predicate<Context> predicate : this.filters) {
            try {
                if (!predicate.test(context)) {
                    return false;
                }
            } catch (final Exception e) {
                throw new PipelineException(
                    String.format(
                        "Failed to evaluate filter '%s' for '%s'",
                        TypeToken.get(predicate.getClass()).getType().getTypeName(),
                        this
                    ),
                    e
                );
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull final ServiceWrapper<Context, Result> o) {
        return Boolean.compare(o.defaultImplementation, this.defaultImplementation);
    }

    @Override
    public String toString() {
        return String.format(
            "ServiceWrapper{type=%s,implementation=%s}",
            this.serviceType.getType().getTypeName(),
            this.implementation
        );
    }
}
