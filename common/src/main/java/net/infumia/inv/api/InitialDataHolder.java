package net.infumia.inv.api;

import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import org.jetbrains.annotations.NotNull;

public interface InitialDataHolder {
    @NotNull
    TypedKeyStorageImmutable initialData();
}
