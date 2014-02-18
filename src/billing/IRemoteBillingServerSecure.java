package billing;

import java.rmi.Remote;
import java.rmi.RemoteException;

import management.SecureCommand;
import Exceptions.IllegalNumberOfArgumentsException;
import Exceptions.PriceStepIntervalOverlapException;
import Exceptions.WrongInputException;
import ServerModel.Auction;

public interface IRemoteBillingServerSecure extends Remote{

	public <T> T executeSecureCommand (SecureCommand<T> sc, String[] cmd) throws IllegalNumberOfArgumentsException, WrongInputException, PriceStepIntervalOverlapException, RemoteException;

	public void billAuction(String name, int id, double highestBid);
}
