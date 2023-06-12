package filmai.model;

public class RequestForm {
    private int id;

    private String category;
    private String title;
    private String description;
    private String rating;
    private int userId;

    public RequestForm() {
    }

    public RequestForm(int id, String category, String title, String description, String rating, int userId) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.userId = userId;
    }

    public RequestForm(String category, String title, String description, String rating, int userId) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}