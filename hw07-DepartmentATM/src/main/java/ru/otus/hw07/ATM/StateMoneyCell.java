package ru.otus.hw07.ATM;

import ru.otus.hw07.ATM.MoneyCell.MoneyCellImp;
import java.util.HashMap;
import java.util.Map;

class StateMoneyCell {

    private  MoneyCellImp moneyCellEmpty;

    MoneyCellImp getMoneyCellEmpty() {
        return moneyCellEmpty;
    }

    MoneyCellImp getMoneyCellInit() {
        return moneyCellInit;
    }

    private final MoneyCellImp moneyCellInit;

    StateMoneyCell(MoneyCellImp moneyCellInit) {
        this.moneyCellInit = moneyCellInit;
        createMoneyCellEmpty();
    }

    private void createMoneyCellEmpty() {
        Map<MoneyValue,Integer> mapMoney = new HashMap<>();
        this.moneyCellEmpty = new MoneyCellImp(mapMoney);
    }

}
