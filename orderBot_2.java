package newpacselinum;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner; // enter the before the start of any class 

public class orderBot_2 {
	public static void main(String[] args) throws InterruptedException {
		int count = 0;  //counter for current number of failures or exceptions
		int maxTries = 50; // how many failures of any type
		int listFlag = 0; // signal to show if there is more then one keyword
		int i= 0; //just some counter
		List<String> whichItemInputList = new ArrayList<String>();

		String username; // container for a userinput password
		String numberOfItems; // user input for quanity of each
		String whichItems; // items
		// int allItems;
		Scanner input = new Scanner(System.in); // input is just a random variable you can choose any other variable name 
		// scanner is well, scanner
		System.out.println("Enter the password"); //provide user password, could be better way to protect this input
		username = input.next(); //scanner call to store input to username
		System.out.println("How many items do you want to get each"); // prompt
		numberOfItems = input.next(); //scanner call
		System.out.println("What items are you looking for [Enter with no spaces and comma ex: itm1,itm2"); //basically asking for keywords
		whichItems = input.next().toLowerCase(); //best practice 
		input.close(); //no more user input, close instance
		System.out.println(whichItems); //mostly testing purposes
		boolean itemInput = whichItems.contains(","); // just detecting if more then one, if user follows the rules, 

		//should develop for if violation occurs such as spaces

		if (itemInput == true) { //if something entered . declared just before, as a flag for a list
			List<String> someList = Arrays.asList(whichItems.split(",")); //need something to point to
			whichItemInputList.addAll(someList); //define list
			listFlag = 1;
		} 

		WebDriver driver; //establist driver per selenium
		System.setProperty("webdriver.gecko.driver", "HDPATHC:\\geckodriverfilename.exe"); //"C:\\\\geckodriver\\geckodriver.exe"); //point to web driver location on hd, from selenium download
		driver = new FirefoxDriver(); //call method
		String baseURL = "https://www.SOMEWEBSITEHERE"; //website, opturnity to define as user input above
		driver.get(baseURL); //call url , opp to properly define above

		// login
		driver.findElement(By.id("email")).sendKeys("lgrech1225@gmail.com"); // login on page, userid type
		driver.findElement(By.id("password")).sendKeys(username); //user password in this case... defined above

		driver.findElement( //looking for element via xpath, buttion to signin here
				By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div/header/div[2]/div/form/fieldset/div[3]/button")) // found using dev tools
		.click(); //once found click

		// design of page currently product class-> size-> qty
		driver.get("https://www."); // actual page to look for products here
		// Thread.sleep(5000);

		List<WebElement> rows = driver.findElements(By.className("size-name")); //build a list - driver is our session call find elements, is counter -- each item has its own name -- rows is just random varriable name here
		System.out.println("Total selected rows are " + rows.size());
		// System.out.println("the total collums are " + columns.size()); //testing
		// Now using Iterator we will iterate all elements
		Iterator<WebElement> iter = rows.iterator(); // itterator call ... using the above counted rows ... opp to turn into class obv
		Iterator<String> iter2 = whichItemInputList.iterator(); // this is the list of items from user, could probaly turn this into a class also

		// this will check whether list has some element or not

		while (iter.hasNext() && iter2.hasNext()) { // remeber what these are, while we still have another item in the list
			try { //while try loop for list
				if (listFlag >= 1) { //defined above when user enters list
					//Iterator<String> iterList = whichItemInputList.iterator(); //just general method, changed for list
					// Iterate one by one
					WebElement item = iter.next(); //webelement was defined above in our row count item name list we built
					if (i++ == rows.size()) { //this is testing only watching 
						System.out.println(iter2.next()); //this is testing only watching 
					}
					//List<String> itemInputList = iterList.next(); //old
					String productName = driver.findElement(By.className("product-info-text")).getText();  // what am i looking at currently

					// If this for only pickign certian items

					boolean itemFilterTF = iter2.toString().contains(productName.toLowerCase()); //iter2 is defined above, input list, need to convert and pull string for comparing
					System.out.println(productName); //testing
					System.out.println(item); //testing
					System.out.println(iter); //testing
					System.out.println(iter2.getClass().getName()); //testing
					System.out.println(itemFilterTF); //testing
					if (itemFilterTF == true) { //if there is a name
						item.click(); //item is current page item name list
						Thread.sleep(100); // web delay in loading
						driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[3]/input")).clear(); // usually default value here cler it 
						driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[3]/input")).sendKeys(numberOfItems); //user defined items type it
						driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[3]/button[3]")).click(); //click add to cart button
						Thread.sleep(1500); //loading because the site does not move on to next item until its loaded in cart ..web delay
					}
					else  
					{
						System.out.println(iter2); //tesing just to see if im in else condition

					}
				} 
				//System.out.println(productName); //testing only
			} catch (Exception e) { //Error routine... maxTries defined above- how man failures will you accept, this is a catch all
				if (++count == maxTries) //not the best error method
					throw e;
			} //end catch
		} //end  while
		//System.out.println("hold");  //testing only
	} //end main 
} //end class
