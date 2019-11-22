package ru.otus.hw06.ATM.MoneyWithdraw;


import ru.otus.hw06.ATM.ATMImp;

public class MoneyWithdrawImp implements  MoneyWithdrawInterface{

    private int unit20 = 0;
    private int unit50 = 0;
    private int unit100 = 0;
    private int unit200 = 0;

    public void setUnit(Integer unitMoneyValue, Integer unitMoneyCount) {
        switch (unitMoneyValue) {
            case 20:
                 unit20 = unit20 + unitMoneyCount;
                 break;
            case 50:
                unit50 = unit50 + unitMoneyCount;
                break;
            case 100:
                unit100 = unit100 + unitMoneyCount;
                break;
            case 200:
                unit200 = unit200 + unitMoneyCount;
                break;
        }
    }

    public Integer getUnit(ATMImp.MoneyValue moneyValue) {
        switch (moneyValue) {
            case unit20:
                return unit20;
            case unit50:
                return unit50;
            case unit100:
                return unit100;
            case unit200:
                return unit200;
            default:
                return 0;
        }
    }

    public void printMoneyUnitForWithdraw() {

        int sum = 0;

        if (unit20 != 0) {
            System.out.println("Будет выдано количество купюр номиналом 20 - " + unit20);
            sum = sum + unit20 * 20;
        }
        if (unit50 != 0) {
            System.out.println("Будет выдано количество купюр номиналом 50 - " + unit50);
            sum = sum + unit50 * 50;
        }
        if (unit100 != 0) {
            System.out.println("Будет выдано количество купюр номиналом 100 - " + unit100);
            sum = sum +  unit100 * 100;
        }
        if (unit200 != 0) {
            System.out.println("Будет выдано количество купюр номиналом 200 - " + unit200);
            sum = sum + unit200 * 200;
        }

        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств для выдачи = " + sum);
        }
    }


}
