package screenmatch.models;

import screenmatch.calc.Rating;

public class Series extends Title implements Rating {
    private final Integer numberOfSeasons;
    private final Boolean activeStatus;

    public Series(String name, Integer yearOfRelease, Integer numberOfSeasons, Boolean activeStatus) {
        super(name, yearOfRelease);
        this.numberOfSeasons = numberOfSeasons;
        this.activeStatus = activeStatus;
    }
    public Series(OmdbAPITitle omdbTitle) {
        super(omdbTitle.Title(), Integer.parseInt(omdbTitle.Year().substring(0, 4)));
        if (omdbTitle.totalSeasons().equalsIgnoreCase("N/A")){
            this.numberOfSeasons = 0;
        }
        else {
            this.numberOfSeasons = Integer.parseInt(omdbTitle.totalSeasons());
        }
        this.activeStatus = omdbTitle.Year().length() <= 5;
        try {
            this.inputRating(
                    Double.parseDouble(omdbTitle.imdbRating()),
                    Integer.parseInt(omdbTitle.imdbVotes().replace(",", ""))
            );
        } catch (NumberFormatException e) {
            System.out.println("Erro" + e.getMessage());
        }
    }
    public Double getRating(){
        return rating;
    }
    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }
    public Boolean getActiveStatus() {
        return activeStatus;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (activeStatus){
            sb.append(" (A)");
        }
        sb.append(" - ").append(yearOfRelease);
        sb.append(" - ").append(this.numberOfSeasons).append(" total seasons");
        sb.append(" - ").append(rating).append(" rating from ");
        sb.append(numberOfRatings).append(" users");
        return sb.toString();
    }
}
