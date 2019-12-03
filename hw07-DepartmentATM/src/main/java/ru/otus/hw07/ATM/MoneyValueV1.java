package ru.otus.hw07.ATM;

public enum MoneyValueV1 {
        UNIT_10 { public String toString() { return "10"; }},
        UNIT_20 { public String toString() {
                        return "20";
                }},
        UNIT_50 { public String toString() {
                        return "50";
                }},
        UNIT_100 { public String toString() {
                        return "100";
                }},
        UNIT_200 { public String toString() {
                        return "200";
                }},
        UNIT_500 { public String toString() {
                        return "500";
                }}
}

