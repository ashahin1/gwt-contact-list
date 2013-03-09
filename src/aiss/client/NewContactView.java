package aiss.client;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewContactView extends Composite{
	
	final TextBox nameTextBox;
	final TextBox telephoneTextBox;
	final VerticalPanel mainPanel;
	final FlexTable fieldsFlexTable;
	final Button saveButton;
	final Button cancelButton;
	final HorizontalPanel buttonsPanel;

	public NewContactView(final Map<String, String> params){
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		fieldsFlexTable = new FlexTable();
		fieldsFlexTable.setText(0, 0, "Name:");
		fieldsFlexTable.setText(0, 1, "Telephone");
		nameTextBox = new TextBox();
		telephoneTextBox = new TextBox();
		fieldsFlexTable.setWidget(1, 0, nameTextBox);
		fieldsFlexTable.setWidget(1, 1, telephoneTextBox);
		mainPanel.add(fieldsFlexTable);
		saveButton = new Button("Save");
		cancelButton = new Button("Cancel");
		buttonsPanel = new HorizontalPanel();
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(saveButton);

		mainPanel.add(buttonsPanel);
		buttonsPanel.setSpacing(10);
		
		//If params!=null, then we fill the fields with params (click in Edit button)
		//params==null => Add button
		if(params!=null){
			nameTextBox.setText(params.get("name"));
			telephoneTextBox.setText(params.get("telephone"));
		}
		
		//Styles
		mainPanel.addStyleName("contact-list-table");
		
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String name = nameTextBox.getText();
				String telephone = telephoneTextBox.getText();
				if(name.length()>0 && telephone.length()>0){
					//If we are editing a contact
					if(params!=null){
						int row = Integer.parseInt(params.get("row"));
						Contact editedContact = new Contact(name, telephone);
						//We edit the contact
						ContactList.contactListData.set(row-1, editedContact);
						ContactList.anyFormIsOpened = false;
						ContactList.go("list", null);
					//If is a new contact
					}else{
						ContactList.contactListData.add(new Contact(name, telephone));
						ContactList.anyFormIsOpened = false;
						ContactList.go("list", null);
					}
					
				}else{
					Window.alert("Please fill both fields");
				}
				
			}
		});
		
		//We remove  remove the new contact form
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ContactList.anyFormIsOpened = false;
				RootPanel.get().remove(asWidget());
			}
		});
	}
	
	/**
	 * Return this panel
	 */
	public Widget asWidget(){
		return this;
	}
}
