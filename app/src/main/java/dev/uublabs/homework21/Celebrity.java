package dev.uublabs.homework21;

/**
 * Created by Admin on 11/13/2017.
 */

public class Celebrity
{
    String firstName;
    String lastName;
    boolean favorite;

    public Celebrity(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        favorite = false;
    }
    public Celebrity(String firstName, String lastName, String fave)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        if(fave.equals("false"))
            favorite = false;
        else
            favorite = true;
    }
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }



    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "Celebrity{"+ firstName + " " +lastName +
                "}";
    }
}
