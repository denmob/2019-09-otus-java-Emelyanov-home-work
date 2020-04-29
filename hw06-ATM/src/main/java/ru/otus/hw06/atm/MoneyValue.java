package ru.otus.hw06.atm;

public enum MoneyValue {

  UNIT_10 {
    @Override
    public String toString() {
      return "10";
    }
  },
  UNIT_100 {
    @Override
    public String toString() {
      return "100";
    }
  },
  UNIT_20 {
    @Override
    public String toString() {
      return "20";
    }
  },
  UNIT_200 {
    @Override
    public String toString() {
      return "200";
    }
  },
  UNIT_50 {
    @Override
    public String toString() {
      return "50";
    }
  },
  UNIT_500 {
    @Override
    public String toString() {
      return "500";
    }
  }

}

