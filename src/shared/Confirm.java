package shared;

public class Confirm implements IConfirm {

	@Override
	public boolean confirm(String pass1, String pass2) {
		if (pass1.equals(pass2)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean amountEnteredByUser(String[] arr, int amountNeeded) {
		if (arr.length == amountNeeded) {
			return true;
		}
		return false;
	}

}
