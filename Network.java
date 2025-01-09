/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if(name != null) {
            for(int i = 0; i<this.userCount;i++) {
                if(this.users[i].getName().toLowerCase().equals(name.toLowerCase())){
                    return this.users[i];
                }
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if(this.userCount < this.users.length && this.getUser(name) == null) {
            this.users[this.userCount++] = new User(name);
            return true;
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User user1 = this.getUser(name1);
        User user2 = this.getUser(name2);
        if(user1 != null && user2 != null && user1 != user2) {
            if(user1.addFollowee(name2)) {
                return true;
            }
        }
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User mostRecommendedUserToFollow = null;
        User current = this.getUser(name);
        if(current == null) {
            return null;
        }
        int maxMutuals = 0;
        for(int i = 0; i<this.userCount;i++) {
            if(this.users[i] == current) {
                continue;
            }
            int mutualCount = current.countMutual(this.users[i]);
            if(mutualCount > maxMutuals) {
                maxMutuals = mutualCount;
                mostRecommendedUserToFollow = this.users[i];
            }
        
        }
        return mostRecommendedUserToFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        User mostPopularUser = null;
        int maxFollowers = 0;
        for(int i = 0; i<this.userCount; i++) {
            User current = this.users[i];
            int currFolloweeCount = this.followeeCount(current.getName());
            if(currFolloweeCount > maxFollowers) {
                maxFollowers = currFolloweeCount;
                mostPopularUser = current;
            }
        }
        if(mostPopularUser != null) { 
            return mostPopularUser.getName();
        }
        return null;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int times = 0;
        for(int i = 0; i < this.userCount; i++) {
            if(this.users[i].getName() == name) {
                continue;
            }
            if(users[i].follows(name)) {
                times++;
            }
        }
        return times;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "Network:"; 
        for(int i = 0; i<this.userCount;i++) {
            ans += "\n" + this.users[i];
        }
       return ans;
    }
}
