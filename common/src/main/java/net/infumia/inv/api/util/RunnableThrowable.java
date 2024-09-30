package net.infumia.inv.api.util;

@FunctionalInterface
public interface RunnableThrowable {
    void run() throws Throwable;
}
