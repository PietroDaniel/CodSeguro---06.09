import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class CriptografiaAssimetrica {

    private KeyPairGenerator keyGen;
    private KeyPair keyPair;

    public CriptografiaAssimetrica() throws NoSuchAlgorithmException {
        configurarChave();
    }

    private void configurarChave() throws NoSuchAlgorithmException {
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

        String mensagemWallace = "Olá Arthur!";
        String criptografadoWallace = usuarioWallace.criptografar(mensagemWallace, usuarioArthur.getChavePublica());
        System.out.println("Criptografado por Wallace: " + criptografadoWallace);

        String descriptografadoArthur = usuarioArthur.descriptografar(criptografadoWallace, usuarioArthur.getChavePrivada());
        System.out.println("Descriptografado por Arthur: " + descriptografadoArthur);

        String mensagemArthur = "Olá Wallace!";
        String criptografadoArthur = usuarioArthur.criptografar(mensagemArthur, usuarioWallace.getChavePublica());
        System.out.println("Criptografado por Arthur: " + criptografadoArthur);

        String descriptografadoWallace = usuarioWallace.descriptografar(criptografadoArthur, usuarioWallace.getChavePrivada());
        System.out.println("Descriptografado por Wallace: " + descriptografadoWallace);
    }
}
