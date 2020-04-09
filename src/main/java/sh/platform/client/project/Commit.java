package sh.platform.client.project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.util.HttpClientExecutor;

import java.util.Collections;
import java.util.List;

public class Commit {

    private Author author;

    private Committer committer;

    private String message;

    private String tree;

    private List<String> parents;

    public Author getAuthor() {
        return author;
    }

    public Committer getCommitter() {
        return committer;
    }

    public String getMessage() {
        return message;
    }

    public String getTree() {
        return tree;
    }

    public List<String> getParents() {
        if (parents == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(parents);
    }

    Commit() {
    }

    @Override
    public String toString() {
        return "Commit{" +
                "author=" + author +
                ", committer=" + committer +
                ", message='" + message + '\'' +
                ", tree='" + tree + '\'' +
                ", parents=" + parents +
                '}';
    }

    static Commit get(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, Commit.class);
    }
}
