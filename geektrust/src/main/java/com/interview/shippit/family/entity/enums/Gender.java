package com.interview.shippit.family.entity.enums;

import java.util.Locale;

public enum Gender {
    MALE, FEMALE, NON_BINARY;

    public static Gender getGenderbyName(String name) {
        switch (name.toLowerCase()) {
            case "male": return MALE;
            case "female": return FEMALE;
            default: return NON_BINARY;
        }
    }
}
