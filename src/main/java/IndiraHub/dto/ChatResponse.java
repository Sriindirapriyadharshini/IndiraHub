package IndiraHub.dto;

import IndiraHub.model.Product;

import java.util.List;

public class ChatResponse {

    private String reply;
    private List<String> suggestions;
    private List<Product> recommendedProducts;

    public ChatResponse() {
    }

    public ChatResponse(String reply, List<String> suggestions, List<Product> recommendedProducts) {
        this.reply = reply;
        this.suggestions = suggestions;
        this.recommendedProducts = recommendedProducts;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Product> getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(List<Product> recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }
}
