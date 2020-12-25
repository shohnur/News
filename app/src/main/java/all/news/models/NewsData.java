
package all.news.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class NewsData {

    @SerializedName("articles")
    private ArrayList<Article> mArticles;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalResults")
    private Long mTotalResults;

    public ArrayList<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(ArrayList<Article> articles) {
        mArticles = articles;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

}
