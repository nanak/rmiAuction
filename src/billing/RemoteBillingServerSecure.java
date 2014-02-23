package billing;

import java.rmi.RemoteException;

import management.SecureCommand;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import Exceptions.WrongNumberOfArgumentsException;

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
		this.bss=bss;
	}

	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException {
		sc.setBillingServerSecure(bss);
		return sc.execute(cmd);
	}

	public void billAuction(String name, int id, double highestBid) throws RemoteException {
		bss.billAuction(name, id, highestBid);
	}
}
