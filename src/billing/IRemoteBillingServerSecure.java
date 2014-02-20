package billing;

import java.rmi.Remote;
import java.rmi.RemoteException;
import management.SecureCommand;
import Exceptions.WrongNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;

public interface IRemoteBillingServerSecure extends Remote{

	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException, RemoteException;

	public void billAuction(String name, int id, double highestBid) throws RemoteException;
}
