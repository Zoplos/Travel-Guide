package main;

public class RecommendedCity {
	String name;
	int rank;
	public RecommendedCity(String name,int rank) {
		super();
		this.name = name;
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

}
