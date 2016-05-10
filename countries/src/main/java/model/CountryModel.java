package model;

public class CountryModel {
	private String name;
	private String capital;
	private String continent;
	
	public CountryModel(String continent, String capital, String name) {
		this.setCapital(capital);
		this.setContinent(continent);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
	
}
