package de.tu_bs.cs.isf.cbc.util;

public interface Coveree {
    String toJava();

    String getName();

    boolean isFullyCovered();

    boolean isPartiallyCovered();
}
