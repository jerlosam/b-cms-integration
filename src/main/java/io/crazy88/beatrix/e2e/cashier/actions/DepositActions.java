package io.crazy88.beatrix.e2e.cashier.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.cashier.components.DepositComponent;
import io.crazy88.beatrix.e2e.player.components.HeaderComponent;

@Component
public class DepositActions {
	

    @Autowired
    private HeaderComponent header;
    
    @Autowired
    private DepositComponent deposit;


    public void navigateToDepositPage() {
        header.navigateToDeposit();
    }
    
    public boolean isDepositDisplayed() {
    	return deposit.isDepositDisplayed();
    }

    public boolean isFirstTimeDepositDisplayed() {
        return deposit.isFirstTimeDepositDisplayed();
    }
}
