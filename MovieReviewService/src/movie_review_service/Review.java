package movie_review_service;

public class Review {
	
	String username;
	Roles user_role;
	String movie_name;
	double review_score;
	
	public Review(String username, Roles user_role, String movie_name, double review_score)
	{
		this.username = username;
		this.user_role = user_role;
		this.movie_name = movie_name;
		this.review_score = review_score;
	}
	
	public String get_Username()
	{
		return this.username;
	}
	
	public String get_Movie_Name()
	{
		return this.movie_name;
	}

}
