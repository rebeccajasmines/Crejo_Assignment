package movie_review_service;

import java.util.*;

public class movieReviewService {
	
	static List<User> users_list = new ArrayList<User>();
	static List<Movie> movies_list = new ArrayList<Movie>();
	static List<Review> reviews_list = new ArrayList<Review>();
	
	public static void add_User(String username)
	{
		username = username.trim();
		User u = new User(username);
		
		users_list.add(u);		
	}
	
	public static void add_Movie(String movie_text)
	{
		String genres = "";
		
		String movie_name = movie_text.split(" released")[0].replace("\"", "");
		int release_year = Integer.parseInt(movie_text.split("Year ")[1].split(" ")[0].replace(" ", ""));
		
		if(movie_text.contains("Genres"))
		{
			genres = movie_text.split("Genres ")[1].replace("â€œ", "").replace("â€�", "");
		}
		else
		{
			genres = movie_text.split("Genre ")[1].replace("â€œ", "").replace("â€�", "");
		}
		
		Movie m = new Movie(movie_name, release_year, genres);
		movies_list.add(m);
	}
	
	public static void add_Review(String username, String movie_name, int rating)
	{
		try
		{
			int rating_multiplier = 1;
			Roles user_role = Roles.Viewer;
			int current_year = Calendar.getInstance().get(Calendar.YEAR);	
			
			for(Movie m : movies_list)
			{
				if(m.get_Movie_Name().equals(movie_name) && m.get_Release_Year() > current_year)
				{
					throw new Exception("Exception: Movie yet to be released\n"
							+ "Movie name: " + movie_name + " and Release Year: " + m.get_Release_Year() + "\n");
				}
			}
			
			for(Review r : reviews_list)
			{
				if(r.get_Username().equals(username) && r.get_Movie_Name().equals(movie_name))
				{
					throw new Exception("Exception: Multiple reviews not allowed\n"
							+ "Movie name: " + movie_name + " and Username: " + username + "\n");
				}
			}
			
			for(User u : users_list)
			{
				if(u.username.contains(username))
				{
					rating_multiplier = u.update_User_Role();
					user_role = u.role;
				}
			}
			
			rating = rating * rating_multiplier;
			Review r = new Review(username, user_role, movie_name, rating);
			reviews_list.add(r);
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static Map<String, Double> sortByValue(HashMap<String, Double> hm, int max) 
    { 
		LinkedHashMap<String, Double> reverseSortedMap = new LinkedHashMap<>();
		hm.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		
        int count = 0;
        Map<String, Double> target = new TreeMap<String, Double>();
        for (Map.Entry<String, Double> entry:reverseSortedMap.entrySet()) {
           if (count >= max) break;

           target.put(entry.getKey(), entry.getValue());
           count++;
        }
        return target;
    }
	
	public static void top_N_Movies(int n, String genre)
	{
		double total_review_score = 0.0;
		
		HashMap<String, Double> movie_detail = new HashMap<>();
		
		for(Movie m : movies_list)
		{			
			total_review_score = 0.0;
			
			if(m.genre.contains(genre))
			{				
				for(Review r : reviews_list)
				{
					if(r.movie_name.equals(m.movie_name) && r.user_role.equals(Roles.Critic))
					{
						total_review_score += r.review_score;
					}
				}
				
				movie_detail.put(m.movie_name, total_review_score);		
			}
		}
		
		Map<String, Double> sorted_movies_list = sortByValue(movie_detail, n);
		sorted_movies_list.forEach((k,v) -> System.out.println("Movie_Name: " + k + ", Total Review Score: " + v));
		
	}
	
	public static double movie_Avg_Review_Score(String movie_name)
	{
		int count = 0;
		double total_review_score = 0.0, avg_review_score = 0.0;
		
		for(Review r : reviews_list)
		{
			if(r.movie_name.equals(movie_name))
			{
				count += 1;
				total_review_score += r.review_score;
			}
		}
		
		avg_review_score = total_review_score/count;		
		return Math.round(avg_review_score*100.0) / 100.0;
	}
	
	public static double year_Avg_Review_Score(int year)
	{
		int count = 0;
		double total_review_score = 0.0, avg_review_score = 0.0;
		
		for(Movie m : movies_list)
		{
			if(m.release_year == year)
			{
				for(Review r : reviews_list)
				{
					if(r.movie_name.equals(m.movie_name))
					{
						count += 1;
						total_review_score += r.review_score;
					}
				}
			}
		}
		
		avg_review_score = total_review_score/count;		
		return Math.round(avg_review_score*100.0) / 100.0;
	}
	
	public static void call_To_Add_Movies()
	{
		movieReviewService.add_Movie("\"Don\" released in Year 2006 for Genres â€œActionâ€� & â€œComedyâ€�");
		movieReviewService.add_Movie("\"Tiger\" released in Year 2008 for Genre â€œDramaâ€�");
		movieReviewService.add_Movie("\"Padmaavat\" released in Year 2006 for Genre â€œComedyâ€�");
		movieReviewService.add_Movie("\"Lunchbox\" released in Year 2022 for Genre â€œDramaâ€�");
		movieReviewService.add_Movie("\"Guru\" released in Year 2006 for Genre â€œDramaâ€�");
		movieReviewService.add_Movie("\"Metro\" released in Year 2006 for Genre â€œRomanceâ€�");
	}
	
	public static void call_To_Add_Users()
	{
		movieReviewService.add_User("SRK");
		movieReviewService.add_User("Salman");
		movieReviewService.add_User("Deepika");
	}
	
	public static void call_To_Add_Reviews()
	{
		movieReviewService.add_Review("SRK", "Don", 2);
		movieReviewService.add_Review("SRK", "Padmaavat", 8);
		movieReviewService.add_Review("Salman", "Don", 5);
		movieReviewService.add_Review("Deepika", "Don", 9);
		movieReviewService.add_Review("Deepika", "Guru", 6);
		movieReviewService.add_Review("SRK", "Don", 10);
		movieReviewService.add_Review("Deepika", "Lunchbox", 5);
		movieReviewService.add_Review("SRK", "Tiger", 5);
		movieReviewService.add_Review("SRK", "Metro", 7);
		movieReviewService.add_Review("SRK", "Guru", 7);
		movieReviewService.add_Review("Deepika", "Guru", 7);
		movieReviewService.add_Review("Deepika", "Tiger", 8);
		
	}
	
	public static void main(String[] args)
    {
		call_To_Add_Movies();
		call_To_Add_Users();
		call_To_Add_Reviews();
		
		System.out.println("Average review score for the year 2006: " + year_Avg_Review_Score(2006));
		System.out.println("Average review score for the movie Don: " + movie_Avg_Review_Score("Don"));
		
		System.out.println();
		top_N_Movies(2, "Drama");
    }

}


