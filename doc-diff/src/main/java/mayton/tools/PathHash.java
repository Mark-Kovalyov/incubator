package mayton.tools;

import java.util.Objects;

public record PathHash(String path, String hash) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathHash pathHash = (PathHash) o;
        return Objects.equals(hash, pathHash.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
}
