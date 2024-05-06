import java.io.*;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

//models
import com.google.gson.*;
import screenmatch.models.Film;
import screenmatch.models.OmdbAPITitle;
import screenmatch.models.Series;
import screenmatch.models.Title;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<Title> contentList = new LinkedList<>();
    static Gson gson = new Gson();
    static Gson gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    static String apiKey = "YOUR API KEY HERE";

    public static Integer menu(){
        System.out.println("\n### MENU ###");
        System.out.println("[1] Pesquisa");
        System.out.println("[2] Visualizar lista de conteúdos por ordem de nome");
        System.out.println("[3] Visualizar lista de conteúdos por ordem de lançamento");
        System.out.println("[4] Sair");
        System.out.println("[5] Limpar lista");
        System.out.print("->");
        return sc.nextInt();
    }
    public static void addOnList (Title inputTitle) throws IOException {
        String option;
        do {
            System.out.print("\nAdicionar na lista de preferidos (s/n)? ");
            option = sc.nextLine();
            if (option.equalsIgnoreCase("s")){
                contentList.add(inputTitle);
                System.out.println("\nConteúdo adicionado com sucesso!");
            } else if (option.equalsIgnoreCase("n")){
                System.out.println("\nConteúdo NÃO adicionado.");
            } else {
                System.out.println("\nOpção inválida.");
            }
        }while((!option.equalsIgnoreCase("s"))&&(!option.equalsIgnoreCase("n")));
    }
    public static void requestInfo(String contentName) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.omdbapi.com/?t=" + contentName + "&apikey=" + apiKey))
                .build();
        HttpResponse<String> responseInJson = client.send(request, HttpResponse.BodyHandlers.ofString());
        try {
            OmdbAPITitle omdbTitle = gson.fromJson(responseInJson.body(), OmdbAPITitle.class);
            if (omdbTitle.Type().equalsIgnoreCase("movie")){
                Film inputFilm = new Film(omdbTitle);
                System.out.println(inputFilm);
                addOnList(inputFilm);
            } else if (omdbTitle.Type().equalsIgnoreCase("series")){
                Series inputSeries = new Series(omdbTitle);
                System.out.println(inputSeries);
                addOnList(inputSeries);
            }
        } catch (NullPointerException e) {
            System.out.println("Não foi possível encontrar o filme desejado.");
        }
    }
    public static void viewSorted(Integer typeOfSort){
        if (contentList.isEmpty()){
            System.out.println("\nLista de conteúdos está vazia.");
        }
        else {
            switch(typeOfSort){
                case 1:
                    Collections.sort(contentList);
                    break;
                case 2:
                    contentList.sort(Comparator.comparing(Title::getYearOfRelease));
                    break;
            }
            for (Title content : contentList){
                if (content instanceof Film){
                    System.out.println("\nFilme " + content);
                }
                else if (content instanceof Series){
                    System.out.println("\nSérie " + content);
                }
            }
        }
    }
    public static void exit(){
        System.out.println("\nAtualizando base de dados");
        try {
            new FileWriter("database.json", false).close();
            FileWriter jsonDatabase = new FileWriter("database.json");
            jsonDatabase.write(gsonBuilder.toJson(contentList));
            jsonDatabase.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("\nSaindo...");
        sc.close();
        System.exit(1);
    }
    public static void initDatabase() throws FileNotFoundException {
        try {
            if (new File("database.json").exists()){
                JsonArray arrayJson = JsonParser.parseReader(new FileReader("database.json")).getAsJsonArray();
                for (int i=0; i<arrayJson.size(); i++){
                    String identificator = String.valueOf(arrayJson.get(i)).substring(2, 3);
                    if (identificator.equalsIgnoreCase("d")){
                        contentList.add(gsonBuilder.fromJson(arrayJson.get(i), Film.class));
                    } else if (identificator.equalsIgnoreCase("n")){
                        contentList.add(gsonBuilder.fromJson(arrayJson.get(i), Series.class));
                    }
                }
                for (Title content : contentList){
                    System.out.println(content+"\n");
                }
            }
            else {
                new File("database.json");
            }
        } catch (IllegalStateException e) {
            System.out.println("Base de dados vazia.");
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Locale.setDefault(Locale.US);
        initDatabase();
        int option;
        do {
            option = menu();
            switch(option){
                case 1:
                    String contentNameInput;
                    System.out.print("\nNome do conteúdo para pesquisa: ");
                    sc.nextLine();
                    contentNameInput = sc.nextLine().toLowerCase().replaceAll(" ", "+");
                    requestInfo(contentNameInput);
                    break;
                case 2:
                    System.out.flush();
                    viewSorted(1);
                    break;
                case 3:
                    System.out.flush();
                    viewSorted(2);
                    break;
                case 4:
                    System.out.flush();
                    exit();
                    break;
                case 5:
                    contentList.clear();
                    new FileOutputStream("database.json").close();
                    break;
                default:
                    System.out.flush();
                    System.out.println("Opção inválida.");
                    break;
            }
        }while(true);
    }
}