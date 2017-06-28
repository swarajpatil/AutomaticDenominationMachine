package main.java;

import java.util.HashMap;
import java.util.Map;

public class DenominationMachine {

    private Map<Integer, Integer> denominationStock;

    public DenominationMachine() {
        denominationStock = new HashMap<>();
    }

    public Map<Integer, Integer> denominationFor(int amount) throws Exception {
        if (denominationStock.isEmpty())
            throw new DenominationOutOfOrderException();

        if(isLowestDenominationAmount(amount))
            throw new DenominationForLowestAmountNotPossible();

        return getDenominations(amount, new HashMap<>());
    }

    private boolean isLowestDenominationAmount(int amount) {
        for (Integer denomination : denominationStock.keySet()) {
            if(denomination < amount)
                return false;
        }
        return true;
    }

    private Map<Integer, Integer> getDenominations(int amount, HashMap<Integer, Integer> denominations) throws Exception {
        if (amount <= 0)
            return denominations;
        if(availableDenomination(amount) == 0)
            throw new ExactDenominationNotPossible();

        denominations.put(availableDenomination(amount), denominationCount(amount, availableDenomination(amount)));
        int remainingAmount = remainintAmount(amount);
        updateDenominationStock(amount);
        return getDenominations(remainingAmount, denominations);
    }

    private void updateDenominationStock(Integer amount) {
        Integer remainingCount = denominationStock.get(availableDenomination(amount)) - availableDenominationCount(amount);
        denominationStock.put(availableDenomination(amount), remainingCount);
    }

    private int remainintAmount(int amount) {
        return amount - availableDenomination(amount) * availableDenominationCount(amount);
    }

    private int availableDenominationCount(int amount) {
        return denominationCount(amount, availableDenomination(amount));
    }

    private Integer denominationCount(int amount, int availableDenomination) {
        if (denominationStock.get(availableDenomination) >= amount / availableDenomination)
            return amount / availableDenomination;
        else
            return denominationStock.get(availableDenomination);
    }

    private int availableDenomination(int amount) {
        for (Integer denomination : denominationStock.keySet()) {
            if (denomination < amount && denominationStock.get(denomination) > 0)
                return denomination;
        }
        return 0;
    }

    public void loadMoney(Map<Integer, Integer> dailyDenominationStock) {
        denominationStock = dailyDenominationStock;
    }
}
