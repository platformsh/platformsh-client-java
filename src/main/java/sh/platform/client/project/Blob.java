package sh.platform.client.project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.util.HttpClientExecutor;

public class Blob {

    private String sha;

    private long size;

    private String encoding;

    private String content;

    Blob() {
    }

    public String getSha() {
        return sha;
    }

    public long getSize() {
        return size;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "sha='" + sha + '\'' +
                ", size=" + size +
                ", encoding='" + encoding + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    static Blob get(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, Blob.class);
    }
}
