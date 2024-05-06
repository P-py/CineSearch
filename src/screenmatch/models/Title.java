package screenmatch.models;

public class Title implements Comparable<Title> {
    protected String name;
    protected Integer yearOfRelease;
    protected Boolean includedInPlus;
    protected Double rating;
    protected Integer numberOfRatings;

    public Title(String name, Integer yearOfRelease){
        this.name = name;
        this.yearOfRelease = yearOfRelease;
        this.includedInPlus = false;
        this.rating = 0.0;
        this.numberOfRatings = 0;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getYearOfRelease(){
        return yearOfRelease;
    }
    public void setYearOfRelease(Integer yearOfRelease){
        this.yearOfRelease = yearOfRelease;
    }
    public Boolean getIncludedInPlus() {
        return includedInPlus;
    }
    public void setIncludedInPlus(Boolean includedInPlus) {
        this.includedInPlus = includedInPlus;
    }
    public void inputRating(Double ratingValue, Integer numberOfRatings){
        this.rating = ratingValue;
        this.numberOfRatings = numberOfRatings;
    }
    @Override
    public String toString(){
        String sb = name +
                " - " + yearOfRelease +
                " - " + rating + " rating from " +
                numberOfRatings + " users";
        return sb;
    }
    @Override
    public int compareTo(Title other) {
        return this.getName().compareTo(other.getName());
    }
}
