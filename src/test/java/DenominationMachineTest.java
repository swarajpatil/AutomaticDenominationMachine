package test.java;

import main.java.DenominationForLowestAmountNotPossible;
import main.java.DenominationMachine;
import main.java.DenominationOutOfOrderException;
import main.java.ExactDenominationNotPossible;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DenominationMachineTest {

    @Test(expected = DenominationOutOfOrderException.class)
    public void OutOfOrderExceptionWhenMachineIsNotLoadedWithDenomination() throws Exception {
        DenominationMachine denominationMachine = new DenominationMachine();
        denominationMachine.denominationFor(500);
    }

    @Test
    public void DenominationOfAmountUsingMultipleOfSingleAmount() throws Exception {
        DenominationMachine denominationMachine = new DenominationMachine();

        Map<Integer,Integer> dailyDenominationStock = new HashMap<>();
        dailyDenominationStock.put(50,10);
        dailyDenominationStock.put(10,10);
        denominationMachine.loadMoney(dailyDenominationStock);

        Map<Integer,Integer> expectedDenomination = new HashMap<>();
        expectedDenomination.put(50,2);
        assertThat(denominationMachine.denominationFor(100),is(expectedDenomination));
    }

    @Test
    public void DenominationOfAmountUsingMultipleOfDifferentAmount() throws Exception {
        DenominationMachine denominationMachine = new DenominationMachine();

        Map<Integer,Integer> dailyDenominationStock = new HashMap<>();
        dailyDenominationStock.put(50,1);
        dailyDenominationStock.put(10,10);
        denominationMachine.loadMoney(dailyDenominationStock);

        Map<Integer,Integer> expectedDenomination = new HashMap<>();
        expectedDenomination.put(50,1);
        expectedDenomination.put(10,5);

        assertThat(denominationMachine.denominationFor(100),is(expectedDenomination));
    }

    @Test
    public void GetCorrectDenominationAfterMultipleWithdraw() throws Exception {
        DenominationMachine denominationMachine = new DenominationMachine();

        Map<Integer,Integer> dailyDenominationStock = new HashMap<>();
        dailyDenominationStock.put(50,1);
        dailyDenominationStock.put(10,15);
        denominationMachine.loadMoney(dailyDenominationStock);

        Map<Integer,Integer> expectedDenomination = new HashMap<>();
        expectedDenomination.put(50,1);
        expectedDenomination.put(10,5);

        assertThat(denominationMachine.denominationFor(100),is(expectedDenomination));

        Map<Integer,Integer> nextExpectedDenomination = new HashMap<>();
        nextExpectedDenomination.put(10,10);

        assertThat(denominationMachine.denominationFor(100),is(nextExpectedDenomination));
    }

    @Test (expected =  ExactDenominationNotPossible.class)
    public void ThrowExceptionWhenExactChangeIsNotPossible() throws Exception{
        DenominationMachine denominationMachine = new DenominationMachine();

        Map<Integer,Integer> dailyDenominationStock = new HashMap<>();
        dailyDenominationStock.put(50,1);
        dailyDenominationStock.put(10,5);
        denominationMachine.loadMoney(dailyDenominationStock);

        Map<Integer,Integer> expectedDenomination = new HashMap<>();
        expectedDenomination.put(50,1);
        expectedDenomination.put(10,5);
        assertThat(denominationMachine.denominationFor(100),is(expectedDenomination));

        denominationMachine.denominationFor(100);
    }

    @Test(expected = DenominationForLowestAmountNotPossible.class)
    public void DenominationOfLowestAmountIsNotPossible() throws Exception {
        DenominationMachine denominationMachine = new DenominationMachine();

        Map<Integer,Integer> dailyDenominationStock = new HashMap<>();
        dailyDenominationStock.put(50,10);
        dailyDenominationStock.put(10,10);
        denominationMachine.loadMoney(dailyDenominationStock);

        denominationMachine.denominationFor(10);
    }
}
