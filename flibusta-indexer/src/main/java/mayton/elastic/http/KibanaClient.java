package mayton.elastic.http;

public interface KibanaClient {

    String searchWildCard(String expr);

    String searchMatchAll(String expr);

    String searchMatchPhrasePrefix(String expr);

}
