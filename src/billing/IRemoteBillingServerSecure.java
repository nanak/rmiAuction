package billing;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.PriceStepIntervalOverlapException;
import exceptions.WrongInputException;
import exceptions.WrongNumberOfArgumentsException;
import management.SecureCommand;

public interface IRemoteBillingServerSecure extends Remote{

	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws WrongNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException, RemoteException;

	public void billAuction(String name, int id, double highestBid) throws RemoteException;
}
