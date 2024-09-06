import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CriptografiaSimetrica {

    private SecretKey chaveSecreta;

    public CriptografiaSimetrica() throws Exception {
        configurarChave();
    }

    private void configurarChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        this.chaveSecreta = keyGen.generateKey();
    }

    public String criptografar(String mensagem) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta);
        byte[] bytesCriptografados = cipher.doFinal(mensagem.getBytes());
        return Base64.getEncoder().encodeToString(bytesCriptografados);
    }

    public String descriptografar(String mensagemCriptografada) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chaveSecreta);
        byte[] bytesDescriptografados = cipher.doFinal(Base64.getDecoder().decode(mensagemCriptografada));
        return new String(bytesDescriptografados);
    }

    public static void main(String[] args) throws Exception {
        CriptografiaSimetrica criptografiaAES = new CriptografiaSimetrica();

        String mensagem = "Esta é uma mensagem secreta!";
        String criptografado = criptografiaAES.criptografar(mensagem);
        System.out.println("Mensagem criptografada: " + criptografado);

        String descriptografado = criptografiaAES.descriptografar(criptografado);
        System.out.println("Mensagem descriptografada: " + descriptografado);
    }
}
