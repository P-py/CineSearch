package screenmatch.models;
import screenmatch.calc.Rating;

public class Film extends Title implements Rating {
    private String directorName;
    private Integer durationInMinutes;

    public Film(OmdbAPITitle omdbTitle){
        super(omdbTitle.Title(), Integer.parseInt(omdbTitle.Year()));
        this.directorName = omdbTitle.Director();
        this.durationInMinutes = Integer.parseInt(omdbTitle.Runtime().split(" ")[0]);
        this.inputRating(
                Double.parseDouble(omdbTitle.imdbRating()),
                Integer.parseInt(omdbTitle.imdbVotes().replaceAll(",", ""))
        );
    }
    public Film(String name, Integer yearOfRelease, String directorName, Integer durationInMinutes) {
        super(name, yearOfRelease);
        this.directorName = directorName;
        this.durationInMinutes = durationInMinutes;
    }
    public String getDirectorName() {
        return directorName;
    }
    public void setDirectorName(String directorName){
        this.directorName = directorName;
    }
    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
    @Override
    public String toString(){
        String sb = name +
                " - " + yearOfRelease +
                " - " + durationInMinutes + " minutes" +
                " - " + directorName +
                " - " + rating + " rating from " +
                numberOfRatings + " users";
        return sb;
    }
    @Override
    public Double getRating() {
        return rating;
    }
}