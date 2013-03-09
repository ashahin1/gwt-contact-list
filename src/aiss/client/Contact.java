package aiss.client;

/**
 * Each row in the contact list
 * @author Jav
 *
 */
public class Contact {
	private String name;
	private String telephone;
	
	public Contact(String name, String telephone) {
		super();
		this.name = name;
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	

	@Override
	public String toString() {
		return "Contact [name=" + name + ", telephone=" + telephone + "]";
	}
	
	
	
	
}

