package restrauntClasses;
import java.util.Vector;

public final class Restaurant {

	String name;
	String address;
	Double latitude;
	Double longitude;
	Vector<String> menu;
	
	Restaurant(String name, String address, Double latitude, Double longitude, Vector<String> menu)
	{
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.menu = menu;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String newAddress)
	{
		address = newAddress;
	}
	
	public Double getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(Double newLatitude)
	{
		latitude = newLatitude;
	}
	
	public Double getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(Double newLongitude)
	{
		longitude = newLongitude;
	}
	
	public Vector<String> getMenu()
	{
		return menu;
	}
	
	public void setMenu(Vector<String> newMenu)
	{
		menu = newMenu;
	}
	

}
