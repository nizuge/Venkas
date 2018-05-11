package cn.nizuge.quadrant.pojo;

public class KeyPairBase64 {

    private int kpId;
    private String privateKey;
    private String publicKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getKpId() {
        return kpId;
    }

    public void setKpId(int kpId) {
        this.kpId = kpId;
    }
}


