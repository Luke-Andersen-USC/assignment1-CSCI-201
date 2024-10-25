package restrauntClasses;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public class Restaurants {

	Vector<Restaurant> data; //List<Restaurant>
	//Collection.sort
	
	public boolean restaurantExists(String name)
	{
		for(int i = 0; i < data.size(); i++)
		{
			if(name.equalsIgnoreCase(data.get(i).getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	public Vector<Restaurant> fetchRestaurants(String name)
	{
		Vector<Restaurant> restsFound = new Vector<Restaurant>();
		for(int i = 0; i < data.size(); i++)
		{
			String restName = data.get(i).getName().toLowerCase();
			if(restName.contains(name.toLowerCase()))
			{
				restsFound.add(data.get(i));
			}
		}
		
		if(restsFound.isEmpty())
		{
			return null;
		}
		
		return restsFound;	
	}
	
	public SortedMap<String,Vector<String>> fetchRestaurantsByMenuItem(String item)
	{
		SortedMap<String,Vector<String>> restsFound = new TreeMap<>();
		for(int i = 0; i < data.size(); i++)
		{
			Restaurant rest = data.get(i);
		
			for(int j = 0; j < rest.menu.size(); j++)
			{
				String menuItem = rest.menu.get(j).toLowerCase();
				if(menuItem.contains(item.toLowerCase()))
				{
					
					if(restsFound.get(rest.getName()) == null)
					{
						Vector<String> vec = new Vector<String>();
						restsFound.put(rest.getName(), vec);
					}
					restsFound.get(rest.getName()).add(menuItem);
				}
			}
		}
		
		if(restsFound.isEmpty())
		{
			return null;
		}
		
		return restsFound;	
	}
	
	public void add(Restaurant rest)
	{
		data.add(rest);
	}
	
	
	
	public void remove(String name)
	{
		for(int i = 0; i < data.size(); i++)
		{
			if(name.equalsIgnoreCase(data.get(i).getName()))
			{
				data.remove(i);
			}
		}
	}
	
	public Restaurant getDataByIndex(int i)
	{
		return data.get(i);
	}
	
	public void sortAZ()
	{
		Vector<Restaurant> newList = new Vector<Restaurant>();
		
		for(int i = 0; i < data.size(); i++)
		{
			Restaurant min = data.get(i);
			int minIndex = i;
			
			for(int j = i + 1; j < data.size(); j++)
			{
				if( (min.getName()).compareToIgnoreCase( data.get(j).getName() ) > 0)
				{
					min = data.get(j);
					minIndex = j;
				}
			}
			
			if(minIndex != i)
			{
				Restaurant temp = data.get(minIndex);
				data.set(minIndex, data.get(i));
				data.set(i, temp);
			}
			
			newList.add(min);
			
		}
		
		data = newList;
	}
	
	public void sortZA()
	{
		Vector<Restaurant> newList = new Vector<Restaurant>();
		
		for(int i = 0; i < data.size(); i++)
		{
			Restaurant min = data.get(i);
			int minIndex = i;
			
			for(int j = i + 1; j < data.size(); j++)
			{
				if( (min.getName()).compareToIgnoreCase( data.get(j).getName() ) < 0)
				{
					min = data.get(j);
					minIndex = j;
				}
			}
			
			if(minIndex != i)
			{
				Restaurant temp = data.get(minIndex);
				data.set(minIndex, data.get(i));
				data.set(i, temp);
			}
			
			newList.add(min);
			
		}
		
		data = newList;
	}
	
	public void sortCF(double userLat, double userLong)
	{
		Vector<Restaurant> newList = new Vector<Restaurant>();
		
		for(int i = 0; i < data.size(); i++)
		{
			Restaurant min = data.get(i);
			int minIndex = i;
			double minDist = DistanceCalc(userLat, userLong, min.getLatitude(), min.getLongitude());
			
			for(int j = i + 1; j < data.size(); j++)
			{
				double compDist = DistanceCalc(userLat, userLong, data.get(j).getLatitude(), data.get(j).getLongitude());
				
				if(compDist < minDist)
				{
					min = data.get(j);
					minDist = DistanceCalc(userLat, userLong, min.getLatitude(), min.getLongitude());
					minIndex = j;
				}
			}
			
			if(minIndex != i)
			{
				Restaurant temp = data.get(minIndex);
				data.set(minIndex, data.get(i));
				data.set(i, temp);
			}
			
			newList.add(min);
			
		}
		
		data = newList;
	}
	
	public void sortFC(double userLat, double userLong)
	{
		Vector<Restaurant> newList = new Vector<Restaurant>();
		
		for(int i = 0; i < data.size(); i++)
		{
			Restaurant min = data.get(i);
			int minIndex = i;
			double minDist = DistanceCalc(userLat, userLong, min.getLatitude(), min.getLongitude());
			
			for(int j = i + 1; j < data.size(); j++)
			{
				double compDist = DistanceCalc(userLat, userLong, data.get(j).getLatitude(), data.get(j).getLongitude());
				
				if(compDist > minDist)
				{
					min = data.get(j);
					minDist = DistanceCalc(userLat, userLong, min.getLatitude(), min.getLongitude());
					minIndex = j;
				}
			}
			
			if(minIndex != i)
			{
				Restaurant temp = data.get(minIndex);
				data.set(minIndex, data.get(i));
				data.set(i, temp);
			}
			
			newList.add(min);
			
		}
		
		data = newList;
	}
	
	static double DistanceCalc(double lat1, double long1, double lat2, double long2)
	{
		double dist = 3963.0 * Math.acos((Math.sin(lat1) * Math.sin(lat2)) + 
				Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2 - long1));
		return (double)Math.round(10 * dist) / 10;
	}
	
}
