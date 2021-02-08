package movie_review_service;

public class Movie {
	String movie_name;
	int release_year;
	String genre;
	
	public Movie(String movie_name, int release_year, String genre)
	{
		this.movie_name = movie_name;
		this.release_year = release_year;
		this.genre = genre;
	}
	
	public String get_Movie_Name()
	{
		return this.movie_name;
	}
	
	public int get_Release_Year()
	{
		return this.release_year;
	}
	
}
