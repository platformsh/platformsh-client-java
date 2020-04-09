package sh.platform.client.project;

public class Blob {

    private String sha;

    private long size;

    private String encoding;

    private String content;


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
}
