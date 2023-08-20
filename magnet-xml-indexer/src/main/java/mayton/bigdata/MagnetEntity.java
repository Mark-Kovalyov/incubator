package mayton.bigdata;

public class MagnetEntity {

    public String name;
    public long   size;
    public String lastModified;
    public String md5;
    public String sha1;
    public String md4;
    public String treeTiger;
    public String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getMd4() {
        return md4;
    }

    public void setMd4(String md4) {
        this.md4 = md4;
    }

    public String getTreeTiger() {
        return treeTiger;
    }

    public void setTreeTiger(String treeTiger) {
        this.treeTiger = treeTiger;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
