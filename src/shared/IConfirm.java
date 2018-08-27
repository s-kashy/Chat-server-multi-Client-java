package shared;

public interface IConfirm {
	boolean confirm(String pass, String cpass);

	boolean amountEnteredByUser(String[] arr, int amountNeeded);
}
