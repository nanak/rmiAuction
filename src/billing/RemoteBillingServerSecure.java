package billing;

import java.rmi.Remote;

import management.SecureCommand;
import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import ServerModel.Auction;

/**
 * BillingServer RMI object (which basically just provides login capability)
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class RemoteBillingServerSecure implements IRemoteBillingServerSecure {

	private BillingServerSecure bss;
	
	public RemoteBillingServerSecure(BillingServerSecure bss){
		// WOOOHER?
		this.bss=bss;
	}

	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException {
		sc.setBillingServerSecure(bss);
		return sc.execute(cmd);
	}


	@Override
	public void billAuction(String name, int id, double highestBid) {
		// TODO Auto-generated method stub
		
	}

}
