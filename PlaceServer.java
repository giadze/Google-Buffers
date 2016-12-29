import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import PlaceData.PlaceDataProto.*;

public class PlaceServer {
	public static void main(String args[]) {

		if (args.length > 1) {
			System.err.println("usage: java PlaceServer <rmi_port>");
			System.exit(1);
		}

		int port = 1099;
		if (args.length == 1) {

			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {

				System.err.println("Error: the argument passed for rmi_port is not a valid port");
				System.exit(1);
			}
		}

		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());

		try {

			String url = "//localhost:" + port + "/Places";
			System.out.println("binding " + url);
			Naming.rebind(url, new Places());
			System.out.println("server " + url + " is running...");
		}
		catch (Exception e) {
			System.out.println("Place server failed:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
