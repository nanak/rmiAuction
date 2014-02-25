package billing;

import java.rmi.RemoteException;

import exceptions.PriceStepIntervalOverlapException;
import exceptions.WrongInputException;
import exceptions.WrongNumberOfArgumentsException;
import management.SecureCommand;

/**
 * BillingServer RMI object (which basically just provides login capability)
 * @author Rudolf Krepela
 * @email rkrepela@student.tgm.ac.at
 * @version 11.02.2014
 *
 */
public class RemoteBillingServerSecure implements IRemoteBillingServerSecure {

	private BillingServerSecure bss;
	
	/**
	 * Initialieses the RemoteBillngServerSecure
	 * @param bss	BillingServerSecure in order to execute the secure COmmands
	 */
	public RemoteBillingServerSecure(BillingServerSecure bss){
		this.bss=bss;
	}

	/*
	 * @see billing.IRemoteBillingServerSecure#executeSecureCommand(management.SecureCommand, java.lang.String[])
	 */
	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException {
		sc.setBillingServerSecure(bss);
		return sc.execute(cmd);
	}
	/*
	 * @see billing.IRemoteBillingServerSecure#billAuction(java.lang.String, int, double)
	 */
	public void billAuction(String name, int id, double highestBid) throws RemoteException {
		bss.billAuction(name, id, highestBid);
	}
}
