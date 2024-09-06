import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class CriptografiaAssimetrica {
    private KeyPairGenerator keyGen;
    private KeyPair keyPair;

    public CriptografiaAssimetrica() throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(2048);
        this.keyPair = this.keyGen.generateKeyPair();
    }

    public String criptografar(String mensagem, PublicKey chavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        byte[] bytesCriptografados = cipher.doFinal(mensagem.getBytes());
        return Base64.getEncoder().encodeToString(bytesCriptografados);
    }

    public String descriptografar(String mensagemCriptografada, PrivateKey chavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        byte[] bytesDescriptografados = cipher.doFinal(Base64.getDecoder().decode(mensagemCriptografada));
        return new String(bytesDescriptografados);
    }

    public PublicKey getChavePublica() {
        return keyPair.getPublic();
    }

    public PrivateKey getChavePrivada() {
        return keyPair.getPrivate();
    }

    public static void main(String[] args) throws Exception {
        CriptografiaAssimetrica usuarioWallace = new CriptografiaAssimetrica();
        CriptografiaAssimetrica usuarioArthur = new CriptografiaAssimetrica();

        String mensagemWallaceParaArthur = "Olá Arthur!";
        String mensagemCriptografada = usuarioWallace.criptografar(mensagemWallaceParaArthur, usuarioArthur.getChavePublica());
        System.out.println("Mensagem criptografada por Wallace: " + mensagemCriptografada);

        String mensagemDescriptografada = usuarioArthur.descriptografar(mensagemCriptografada, usuarioArthur.getChavePrivada());
        System.out.println("Mensagem descriptografada por Arthur: " + mensagemDescriptografada);

        String mensagemArthurParaWallace = "Olá Wallace!";
        mensagemCriptografada = usuarioArthur.criptografar(mensagemArthurParaWallace, usuarioWallace.getChavePublica());
        System.out.println("Mensagem criptografada por Arthur: " + mensagemCriptografada);

        mensagemDescriptografada = usuarioWallace.descriptografar(mensagemCriptografada, usuarioWallace.getChavePrivada());
        System.out.println("Mensagem descriptografada por Wallace: " + mensagemDescriptografada);
    }
}
