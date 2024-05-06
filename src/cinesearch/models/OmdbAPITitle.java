package screenmatch.models;

public record OmdbAPITitle(
        String Type,
        String Title,
        String Year,
        String Director,
        String Runtime,
        String imdbRating,
        String imdbVotes,
        String totalSeasons
){}
