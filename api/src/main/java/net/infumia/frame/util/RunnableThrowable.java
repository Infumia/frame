package net.infumia.frame.util;

@FunctionalInterface
public interface RunnableThrowable {
    void run() throws Throwable;
}
