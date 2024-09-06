import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CriptografiaSimetrica {
    private SecretKey chaveSecreta;

    public CriptografiaSimetrica() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);  // Pode ajustar o tamanho da chave, como 128, 192, ou 256 bits
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
        String mensagemCriptografada = criptografiaAES.criptografar(mensagem);
        System.out.println("Mensagem criptografada: " + mensagemCriptografada);

        String mensagemDescriptografada = criptografiaAES.descriptografar(mensagemCriptografada);
        System.out.println("Mensagem descriptografada: " + mensagemDescriptografada);
    }
}
