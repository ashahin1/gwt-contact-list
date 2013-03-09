package aiss.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Table of contact list. Load the data of contact list(attribute in ContactList class) in a table.
 * We will call this view when a contact is removed or updated. 
 * @author Jav
 *
 */
public class ContactListView extends Composite{
	
	final FlexTable contactListTable = new FlexTable();
	final HorizontalPanel mainPanel;
	final Button addNewContactButton;
	
	public ContactListView(Map<String, String> params){
		mainPanel = new HorizontalPanel();
		addNewContactButton = new Button("Add");
		initWidget(mainPanel);
		mainPanel.add(contactListTable);
		mainPanel.add(addNewContactButton);
		
		//Fill the table with data of contact list
		fillContactList(contactListTable, ContactList.contactListData);
		//The FlexTable will be waiting for events (clicks) to update or remove rows
		addClickHandler(contactListTable);
		
		//Styles
		contactListTable.addStyleName("contact-list-table");
		mainPanel.setSpacing(10);
		
		addNewContactButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//If the new contact form is closed
				if(!ContactList.anyFormIsOpened){
					ContactList.anyFormIsOpened = true;
					//Click in Add button, will call NewContactView. We want a empty NewContactView,
					//so we pass a null value.
					ContactList.go("add", null);
				}
				
				
			}
		});
		
		
	}
	
	private void addClickHandler(final FlexTable table) {
		table.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	//Clicked cell
		        Cell cell = table.getCellForEvent(event);
		        if(cell!=null){
		        	int columnClicked = cell.getCellIndex();
			        int rowClicked = cell.getRowIndex();
			        //If rowClicked==0, then user clicked in the header
			        if(rowClicked>0){
				        //Click in Delete button (second column)
				        if(columnClicked==2){
				        	if(!ContactList.anyFormIsOpened){
				        		ContactList.anyFormIsOpened = true;
				        		//We apply a style (background color in the row) when user clicks in a button
								//contactListTable.getRowFormatter().addStyleName(rowClicked, "row-selected");
								String contactName = ContactList.contactListData.get(rowClicked-1).getName();
								//We remove the selected row and reload the View.
								if(Window.confirm("Do you want to remove: "+contactName +" ?")){
									ContactList.contactListData.remove(rowClicked-1);
									ContactList.anyFormIsOpened = false;
									ContactList.go("list", null);
								}else{
									
									ContactList.anyFormIsOpened = false;
								}
				        	}
				        	
						//If the user clicks in the Edit button, we retrieve the data in the New Contact panel
						}else if(columnClicked==3){
							if(!ContactList.anyFormIsOpened){
								ContactList.anyFormIsOpened = true;
					        	Contact retrievedContact = ContactList.contactListData.get(rowClicked-1);
					        	Map<String, String> params = new HashMap<String, String>();
					        	params.put("name", retrievedContact.getName());
					        	params.put("telephone", retrievedContact.getTelephone());
					        	params.put("row", rowClicked+"");

					        	ContactList.go("add", params);
							}
				        	
				        }
			        }
		        }
		    }
		});
		
	}

	/**
	 * Fill the table using a list of Contact
	 * @param contacts
	 */
	public void fillContactList(FlexTable table, List<Contact> contacts){
		fillFirstRow(table);
		if(contacts!=null){
			//We have to start in row 1 (first row is the header)
			int row=1; 
			for(Contact contact:contacts){
				table.setText(row, 0, contact.getName());
				table.setText(row, 1, contact.getTelephone());
				table.setWidget(row, 2, new Button("Delete"));
				table.setWidget(row, 3, new Button("Edit"));
				row++;
				
				
			}
		}
	}
	
	/**
	 * Fill the first row in the FlexTable (name, telephone, etc)
	 */
	private void fillFirstRow(FlexTable table){
		table.setText(0, 0, "Name");
		table.setText(0, 1, "Telephone");
		table.setText(0, 2, "Action");
		table.setText(0, 3, "Action");
		table.getRowFormatter().getElement(0).setClassName("header");

	}
	
	
}
