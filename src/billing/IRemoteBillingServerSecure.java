package billing;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.PriceStepIntervalOverlapException;
import exceptions.WrongInputException;
import exceptions.WrongNumberOfArgumentsException;
import management.SecureCommand;
/**
 * Interface which every RemoteBillingServer needs to have in order to be exported to RMI
 * @author Daniel Reichmann
 * @version 2014-02-25
 *
 */
public interface IRemoteBillingServerSecure extends Remote{
	/**
	 * 
	 * @param sc	Command which shall be executed
	 * @param cmd	InputArguments for the command
	 * @return	Returns the Value of the execution, depends on Implementation
	 * 
	 * @throws WrongNumberOfArgumentsException	Number of Argument don't match required arguments
	 * @throws WrongInputException	Arguments doesn't match specific formatting
	 * @throws PriceStepIntervalOverlapException	PriceSteps overlap (e.g addPriceStep)
	 * @throws RemoteException		RemoteObject is not available anymore
	 */
	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException, RemoteException;

	/**
	 * AuctionServer calls the BillingServer in order to write a bill for a user
	 * @param name	User who has to pay
	 * @param id	AuctionID
	 * @param highestBid	StrikePrice
	 * @throws RemoteException	BillingServer is not available anymore
	 */
	public void billAuction(String name, int id, double highestBid) throws RemoteException;
}
