package restrauntClasses;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.util.Vector;

import java.util.Scanner;
import java.util.SortedMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.Math;
import java.text.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestaurantSearcher {

	public static void main(String []args)
	{
		//set-up
		Scanner s = new Scanner(System.in);
		
		//file
		
		//Scanner sc;
		boolean goodFile = false;
		String temp = "";
		String fileName = "";
		
		while(!goodFile)
		{
			System.out.println("Provide a JSON File:");
			
			fileName = s.nextLine();
			
			System.out.println(fileName);
			Scanner sc = null;
			
			try
			{
				File file = new File(fileName);
				sc = new Scanner(file);
				goodFile = true;
				
				temp = "";
			
				while(sc.hasNext())
				{
					temp += sc.nextLine();
				}
				
				sc.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("The file " + fileName + " could not be found");
			}
			finally
			{
				if(sc != null)
				{
					sc.close();	
				}
				
			}
			
		}
		
		System.out.println("The file " + fileName + " had been properly read.");
		
		
		
		Gson gson = new Gson();
		
		Restaurants restaurants;
		
		
		restaurants = gson.fromJson(temp, Restaurants.class);
		
		
		
		//user latitude
		Double userLat = 0.0;
		boolean userLatIn = false;
		while(!userLatIn)
		{
			System.out.println("What is your latitude?");	
		
			try
			{
				userLat = Double.parseDouble(s.nextLine());
				userLatIn = true;
			}
			catch(NumberFormatException e)
			{
				System.out.println("That is not a valid option.");
				userLatIn = false;
			}
		}
		
		//user longitude
		Double userLong = 0.0;
		boolean userLongIn = false;
		while(!userLongIn)
		{
			System.out.println("What is your longitude?");	
		
			try
			{
				userLong = Double.parseDouble(s.nextLine());
				userLongIn = true;
			}
			catch(NumberFormatException e)
			{
				System.out.println("That is not a valid option.");
				userLongIn = false;
			}
		}
		
		
		
		//PROGRAM START
		
		
		
		System.out.println("1) Display all restaurants");
		System.out.println("2) Search for a restaurant");
		System.out.println("3) Search for a menu item");
		System.out.println("4) Add a new restaurant");
		System.out.println("5) Remove a restaurant");
		System.out.println("6) Sort restaurants");
		System.out.println("7) Exit");
		
		char input = '0';
		
		while(input != '7')
		{
			
			System.out.println("What would you like to do?");
			
			input = s.nextLine().charAt(0);
			
			if(input == '1')
			{
				for(int i = 0; i < restaurants.data.size();i++)
				{
					Restaurant rest = restaurants.data.get(i);
					double dist = DistanceCalc(userLat, userLong, rest.getLatitude(), rest.getLongitude());
					System.out.println(rest.getName() + ", located " + dist + " miles away at " + rest.getAddress());
				}
			}
			else if(input == '2')
			{
				String name;
				System.out.println("What is the name of the restaruant you would like to search for?");
				
				name = s.nextLine();
				
				Vector <Restaurant> restsFound = restaurants.fetchRestaurants(name);
				if(restsFound != null)
				{
					
					for(int i = 0; i < restsFound.size();i++)
					{
						Restaurant rest = restsFound.get(i);
						double dist = DistanceCalc(userLat, userLong, rest.getLatitude(), rest.getLongitude());
						System.out.println(rest.getName() + ", located " + dist + " miles away at " + rest.getAddress());
					}
				}
				else
				{
					System.out.println("No entry for " + name + " was found.");
				}
			}
			else if(input == '3')
			{
				String itemName;
				System.out.println("What menu item would you like to search for?");
				
				itemName = s.nextLine();
				
				SortedMap<String,Vector<String>> restsFound = restaurants.fetchRestaurantsByMenuItem(itemName);
				
				if(restsFound != null)
				{
					for(String name : restsFound.keySet())
					{
						String output = name + " serves ";
						
						Vector<String> restMenu = restsFound.get(name);
						
						output += restMenu.get(0);
						
						for(int i = 1; i < restMenu.size(); i++)
						{
							output += " and " + restMenu.get(i);
						}
						output += ".";
						
						System.out.println(output);
					}
				}
				else
				{
					System.out.println("No restaurants serving " + itemName + " were found.");
				}
			}
			else if(input == '4')
			{
				boolean validAdd = false;
				while(!validAdd)
				{
					String name;
					System.out.println("What is the name of the restaruant you would like to add?");
					
					name = s.nextLine();
					
					if(restaurants.restaurantExists(name))
					{
						System.out.println("There is already an entry for " + name);
					}
					else
					{
						Restaurant rest = AddRestaurant(name,s);
						restaurants.add(rest);
						validAdd = true;
						
						double dist = DistanceCalc(userLat, userLong, rest.getLatitude(), rest.getLongitude());
						System.out.println("There is now a new entry for:");
						System.out.println(rest.getName() + ", located " + dist + " miles away at " + rest.getAddress());
						
						String output = rest.getName() + " serves ";
						
						Vector<String> restMenu = rest.getMenu();
						
						output += restMenu.get(0);
						
						for(int i = 1; i < restMenu.size(); i++)
						{
							output += " and " + restMenu.get(i);
						}
						output += ".";
						
						System.out.println(output);
						
					}
				}
			}
			else if(input == '5')
			{
				String name;
				System.out.println("What is the name of the restauruant you would like to remove?");
				
				name = s.nextLine();
				
				if(restaurants.restaurantExists(name))
				{
					restaurants.remove(name);
					System.out.println(name + " is now removed.");
				}
				else
				{
					System.out.println("There is no restaurant of that name.");
				}
				
			}
			else if(input == '6')
			{
				System.out.println("1). A to Z");
				System.out.println("2). Z to A");
				System.out.println("3). Closest to farthest");
				System.out.println("4). Furthest to closest");
				
				System.out.println("How would you like to sort by?");
				
				char choice = s.nextLine().charAt(0);
				
				if(choice == '1')
				{
					restaurants.sortAZ();
					System.out.println("Your restaurants are now sorted A-Z.");
				}
				else if(choice == '2')
				{
					restaurants.sortZA();
					System.out.println("Your restaurants are now sorted Z-A.");
				}
				else if(choice == '3')
				{
					
					restaurants.sortCF(userLat, userLong);
					System.out.println("Your restaurants are now sorted closest to farthest.");
					
				}
				else if(choice == '4')
				{
					restaurants.sortFC(userLat, userLong);
					System.out.println("Your restaurants are now sorted farthest to closest.");
				}
				else
				{
					System.out.println("That is not a valid option");
				}
				
				
				
			}
			else if(input == '7')
			{
				break;
			}
			else
			{
				System.out.println("That is not a valid option");
			}
			System.out.println(" ");
			System.out.println("1) Display all restaurants");
			System.out.println("2) Search for a restaurant");
			System.out.println("3) Search for a menu item");
			System.out.println("4) Add a new restaurant");
			System.out.println("5) Remove a restaurant");
			System.out.println("6) Sort restaurants");
			System.out.println("7) Exit");
		}
		
		//END OF PROGRAM LOOP
		
		System.out.println("Successfully exited!");
		
		boolean savedDecision = false;
		
		while(!savedDecision)
		{
			
			
			System.out.println("Do you want to Save?");
			System.out.println("1). Yes");
			System.out.println("2). No");
			
			char inputFinal = s.nextLine().charAt(0);
			
			if(inputFinal == '1')
			{
				Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
				String jsonSave = gson.toJson(restaurants);
				try
				{
					FileWriter fr = new FileWriter("restaurants.json");
					fr.write(jsonSave);
					fr.close();
					
					System.out.println("Your edits have been saved to " + fileName);
				}
				catch(IOException e)
				{
					System.out.println("Error overwriting file");
				}
				
				savedDecision = true;
			}
			else if(inputFinal == '2')
			{
				savedDecision = true;
			}
			else
			{
				System.out.println("That is not a valid option");
			}
		}
		
		s.close();
		System.out.println("End of program!");
	}
	
	//END OF MAIN
	
	static Restaurant AddRestaurant(String name, Scanner s)
	{
		System.out.println("What is the address for " + name + "?");
		
		String address = s.nextLine();
		
		
		System.out.println("What is the latitude for " + name + "?");
		
		Double latitude = Double.parseDouble(s.nextLine());
		
		
		System.out.println("What is the longitude for " + name + "?");
		
		Double longitude = Double.parseDouble(s.nextLine());
		
		Vector<String> menu = new Vector<String>();
		
		char input = '1';
		while(input != '2')
		{
			System.out.println("What does " + name + " serve?");
			
			if(input == '1')
			{
				menu.add(s.nextLine());
			}
			else
			{
				System.out.println("That is not a valid option");
			}
			
			
			System.out.println("Does " + name + " serve anything else?");
			System.out.println("1). Yes");
			System.out.println("2). No");
			
			input = s.nextLine().charAt(0);
		}	
		
		Restaurant rest = new Restaurant(name, address, latitude, longitude, menu);
		return rest;
		
	}
	
	
	
	
	static double DistanceCalc(double lat1, double long1, double lat2, double long2)
	{
		double dist = 3963.0 * Math.acos( (Math.sin(lat1) * Math.sin(lat2)) + 
				Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2 - long1) );
		dist = Math.toRadians(dist);
		
		return (double)Math.round(10 * dist) / 10;
	}
	
	
	

}


