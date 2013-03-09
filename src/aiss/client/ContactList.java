package aiss.client;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author Jav
 *
 */
public class ContactList implements EntryPoint {
	
	public static List<Contact> contactListData = new LinkedList<Contact>();
	//To avoid opening more than one form at the same time
	public static boolean anyFormIsOpened;

	public void onModuleLoad() {
		fillWithExamples();
		go("init", null);
		
	}
	
	/**
	 * We will use this method to do the transition between views.
	 * @param token: Name of the view
	 * @param params: Data to load (in some cases, for example while we are editing
	 * a contact)
	 */
	public static void go(String token, Map<String, String> params){
		Panel p = RootPanel.get();
		if (token=="list" || token=="init" ){
			p.clear();
			p.add(new ContactListView(params));
		}else if(token=="add"){
			p.add(new NewContactView(params));
		}else{
			throw new IllegalArgumentException("Wrong parameter: "+token);
		}
	}
	
	private void fillWithExamples(){
		contactListData.add(new Contact("Steve Jobs", "645798653"));
		contactListData.add(new Contact("Steve Wozniak", "615427548"));
		contactListData.add(new Contact("Larry Page", "629588976"));
		contactListData.add(new Contact("Alan Turing", "685388976"));
		contactListData.add(new Contact("Linus Torvalds", "645785946"));
		contactListData.add(new Contact("Charles Babbage", "645821546"));


	}
	
	
	
	
}
