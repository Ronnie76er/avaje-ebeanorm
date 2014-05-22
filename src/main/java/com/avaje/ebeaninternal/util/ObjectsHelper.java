package com.avaje.ebeaninternal.util;

public class ObjectsHelper {

    // Returns the hash code of a non-null argument and 0 for a null argument.
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    // Returns true if the arguments are equal to each other and false otherwise.
    // Consequently, if both arguments are null, true is returned and if exactly one argument is null, false is returned.
    // Otherwise, equality is determined by using the equals method of the first argument.
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
