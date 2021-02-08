package movie_review_service;
enum Roles
{
	Viewer,
	Critic,
	Expert,
	Admin
}

public class User {
	
	String username;
	Roles role;
	int review_count;

	public User(String username)
	{
		this.username = username;
		this.role = Roles.Viewer;
		this.review_count = 0;
	}
	
	public int update_User_Role()
	{
		int rating_multiplier = 1;
		this.review_count += 1;
		
		if(this.review_count >= 3 && this.review_count <= 10)
		{
			this.role = Roles.Critic;
			rating_multiplier = 2;
			if(this.review_count == 3)
			{
				System.out.println("User " + this.username + " promoted to " + this.role + " for " + this.review_count + " reviews\n");
			}
			
		}
		else if(this.review_count >= 11 && this.review_count <= 25)
		{
			this.role = Roles.Expert;
			rating_multiplier = 3;
			if(this.review_count == 11)
			{
				System.out.println("User " + this.username + " promoted to " + this.role + " for " + this.review_count + " reviews\n");
			}
		}
		else if(this.review_count >= 26)
		{
			this.role = Roles.Admin;
			rating_multiplier = 4;
			if(this.review_count == 26)
			{
				System.out.println("User " + this.username + " promoted to " + this.role + " for " + this.review_count + " reviews\n");
			}
		}
		return rating_multiplier;
	}
}
